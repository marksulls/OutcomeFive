package com.retro.core.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.sql.DataSource;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.retro.core.data.DataException;
import com.retro.core.data.Entity;
import com.retro.core.data.GeneratedStringKeyHolder;

/*
import com.google.appengine.api.memcache.AsyncMemcacheService;
import com.google.appengine.api.memcache.InvalidValueException;
import com.google.appengine.api.memcache.MemcacheService;
*/

/**
 * Base data access object for our data layer.
 * 
 * @author Mark Sullivan
 **/
public abstract class BaseDao<T extends Entity> {
    // logging (this has to be static otherwise it will not deserialize correctly)
    final static Logger _log = LoggerFactory.getLogger(BaseDao.class);
    /* hook to the spring jdbc template */
    protected JdbcTemplate jdbcTemplate;
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    // transaction manager
    private PlatformTransactionManager transactionManager;
    // the cache
    private MemcachedClient cache;
    // standard mysql date formatter
    private static final DateFormat mysqlDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    // default cache time (7 days)
    private final static int CACHE_TIME = 0;
    /**
     * The table name inside our database.
     */
    protected String tableName;

    public abstract RowMapper<T> getRowMapper();
    

    public abstract PreparedStatementCreator getSavePreparedStatementCreator(T object);
    
    public void completeObject(T object) {
        // by default do nothing
    }
    
    /**
     * override this if the table doesn't have an auto-incrementing key
     * @return
     */
    public boolean hasAutoKey() {
        return true;
    }

    public PreparedStatementCreator getDeletePreparedStatementCreator(final T object) {
        return new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                _log.info("preparing DELETE FROM " + tableName + " WHERE " + tableName + "_id = ?");
                PreparedStatement ps = connection.prepareStatement("DELETE FROM " + tableName + " WHERE " + tableName + "_id = ?");
                ps.setLong(1, object.getId());
                return ps;
            }
        };
    }

    /**
     * Asynchronously clears the cache entries related to the provided object.
     * This method uses uses getCacheKeysToDelete() to find out which entries to
     * clear.
     * 
     * @param object
     * @throws CacheException 
     * @throws TimeoutException 
     * @throws MemcachedException 
     * @throws InterruptedException 
     */
    protected final void clearCache(T object) throws InterruptedException, MemcachedException, TimeoutException {
        getCache().deleteWithNoReply(getObjectCacheKey(object));
    }

    /**
     * Returns a list of cache keys that should be deleted when an object has
     * been changed. If a child class creates custom cache entries that are not
     * automatically evicted it should override this method and add the custom
     * cache entries to this list.
     * 
     * @param object
     * @return
     */
    protected List<String> getCacheKeysToDelete(T object) {
        // sanity check
        if(object.getId() == null) {
            // skip
            return null;
        }
        ArrayList<String> keys = new ArrayList<String>();
        keys.add(getObjectCacheKey(object));
        return keys;
    }

    /**
     * Defaults to select everything from the DAO's table.
     * 
     * @return
     */
    public String getAllObjectsSql() {
        return "SELECT * FROM " + tableName;
    }
    
    /**
     * Defaults to using the tableName_id as the primary key's column name.
     * 
     * @return
     */
    public String getObjectByIdSql() {
        return getAllObjectsSql() + " WHERE " + tableName + "_id = ?";
    }

    protected String getCommaSeperatedPlaceholds(int totalPlaceholders) {
        StringBuffer placeholders = new StringBuffer();
        for (int i = 1; i <= totalPlaceholders; i++) {
            if (i != totalPlaceholders) {
                placeholders.append("?, ");
            } else {
                placeholders.append("?");
            }
        }
        return placeholders.toString();
    }

    /**
     * Creates a cache key for the objectId. Defaults to tableName_[objectId].
     * 
     * @param objectId
     * @return
     */
    public String getObjectCacheKey(T object) {
        return new StringBuilder(tableName).append("_id_").append(object.getId()).toString();
//        return Base64.encode(new StringBuilder(tableName).append("|id_").append(objectId).toString().getBytes());
    }
    
    public String getObjectIdCacheKey(long id) {
        return new StringBuilder(tableName).append("_id_").append(id).toString();
//        return Base64.encode(new StringBuilder(tableName).append("|id_").append(objectId).toString().getBytes());
    }

    /**
     * Deletes the object from the database and clears the cache.
     * 
     * @param object
     */
    public int deleteObject(T object) {
        _log.info("deleting object id [{}]",object);
        try {
            // clear the cache
            clearCache(object);
        } catch (TimeoutException e) {
            _log.error("error deleting cache for object [{}] - [{}]",object,e);
        } catch (InterruptedException e) {
            _log.error("error deleting cache for object [{}] - [{}]",object,e);
        } catch (MemcachedException e) {
            _log.error("error deleting cache for object [{}] - [{}]",object,e);
        }
        int result = 0;
        // get the connection
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
         // explicitly setting the transaction name 
        def.setName("DeleteTX");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
       
        // start the transaction
        TransactionStatus status = getTransactionManager().getTransaction(def);
        try {
            // do the delete
            result = this.jdbcTemplate.update(getDeletePreparedStatementCreator(object));
            // commit the transaction
            getTransactionManager().commit(status);
        } catch (Exception e) {
            _log.error("error deleting object [{}] - [{}]",object,e);
            getTransactionManager().rollback(status);
        }
        return result;
    }

    /**
     * Returns a list of all the objects of the given type.
     * 
     * @return list of all the objects for the give type
     */
    public List<T> getAllObjects() {
        _log.debug("looking up all objects");
        // look up the objects
        List<T> objects = this.jdbcTemplate.query(getAllObjectsSql(), getRowMapper());
        // TODO: this probably doesn't squash errors, sanity check
        return objects;
    }

    /**
     * Returns the object with the given ID.
     * 
     * @param objectId the id of the object to look up
     * @return the object, or null if the id is invalid
     */
    public T getObjectById(long objectId) {
        // pass false
        return this.getObjectById(objectId,false);
    }
    /**
     * Returns the object with the given ID.
     * 
     * @param objectId the id of the object to look up
     * @return the object, or null if the id is invalid
     */
    public T getObjectById(long objectId,boolean complete) {
        if (objectId == 0) {
            _log.warn("getObjectById called with 0, returning null");
            return null;
        }
        // generate the cache key
        String cacheKey = getObjectIdCacheKey(objectId);
        _log.debug("looking up object with id [{}]. Checking cache for key [{}]", objectId, cacheKey);
        T object = null;
        try {
            // wrap this, sometimes we see memcached errors
            object = getCache().getAndTouch(cacheKey,CACHE_TIME);
//            object = getCache().get(cacheKey);
        } catch (TimeoutException e) {
            // leave the object as null to be retrieved again
            // TODO: delete this in the future, for now i want to catch this in 
            // production and see what the object looks like
            _log.error("caught memcached exception on key [{}] - [{}]",cacheKey,e);
        } catch (InterruptedException e) {
            _log.error("caught memcached exception on key [{}] - [{}]",cacheKey,e);
        } catch (MemcachedException e) {
            _log.error("caught memcached exception on key [{}] - [{}]",cacheKey,e);
        }
        // check if we found the object
        if (object == null) {
            _log.warn("Cache MISS for key [{}]: [{}]", cacheKey, object);
            try {
                // look up the object
                String sql = getObjectByIdSql();
                _log.debug("SQL: [{}], Object ID: [{}]", sql, objectId);
                object = this.jdbcTemplate.queryForObject(sql, new Object[] {objectId}, getRowMapper());
            } catch (EmptyResultDataAccessException e) {
                _log.warn("no object found with id {}", objectId);
                return null;
            }
            // save to the cache before completing
            try {
                // save for a week
                boolean ret = getCache().add(cacheKey,CACHE_TIME,object);
                _log.debug("add returned [{}] for key [{}], object [{}]", new Object[] {ret,cacheKey,object});
            } catch (TimeoutException e) {
                _log.error("caught memcached exception storing key [{}] - [{}]",cacheKey,e);
            } catch (InterruptedException e) {
                _log.error("caught memcached exception storing key [{}] - [{}]",cacheKey,e);
            } catch (MemcachedException e) {
                _log.error("caught memcached exception storing key [{}] - [{}]",cacheKey,e);
            }
        } else {
            _log.debug("Cache HIT for key [{}]: [{}]", cacheKey, object);
        }
        // should we build out the rest of the object?
        if(complete) {
            // call the builder
            completeObject(object);
        }
        // TODO: this probably doesn't squash errors, sanity check
        _log.debug("returning the object {}", object);
        return object;
    }

    /**
     * executes a save or update for the given object, based on whether or not
     * the ID of the object is set. On an insert, the generated primary key is
     * set in place
     * 
     * @param object
     *            the object to be updated
     * @return true
     * @throws Exception
     */
    public boolean saveOrUpdate(T object) {
        return saveOrUpdate(object,true);
        
    }
    /**
     * executes a save or update for the given object, based on whether or not
     * the ID of the object is set. On an insert, the generated primary key is
     * set in place
     * 
     * @param object the object to be updated
     * @param useTx whether to use transactions
     * @return true
     * @throws Exception
     */
    public boolean saveOrUpdate(T object,boolean useTx) {
        _log.debug("saveOrUpdating the object {}", object);
        // this will end up with our primary key
        KeyHolder keyHolder = new GeneratedStringKeyHolder();
        // transaction structures
        TransactionStatus status = null;
        // check for transactions
        if(useTx) {
            // get the transaction manager
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
             // explicitly setting the transaction name 
            def.setName("SaveOrUpdateTX");
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            // start the transaction
            status = getTransactionManager().getTransaction(def);
        }
        try {
            // check if is key is expected
            if(hasAutoKey()) {
                // do the insert/update
                this.jdbcTemplate.update(getSavePreparedStatementCreator(object), keyHolder);
            } else {
                // non-keyed version
                this.jdbcTemplate.update(getSavePreparedStatementCreator(object));
            }
            if(useTx) {
                // commit the transaction
                getTransactionManager().commit(status);
            }
        } catch (Exception e) {
            _log.error("error in transaction [{}]",e);
            if(useTx) {
                getTransactionManager().rollback(status);
            }
            // rethrow this
            throw new DataException("failed to save object with error - " + e);
        }
        // check primary key instead of affected count
        if (object.getId() == Entity.NULL && hasAutoKey()) {
            // was an insert, set the primary key
            // cast and set it. there is prolly a better way to do this with generics
            _log.debug("saveOrUpdating inserted new record, keyHolder [{}], in class [{}]", keyHolder,this.getClass().getName());
            // sanity check the table schema
            if(keyHolder.getKey() != null) {
                // this will throw a NPE, so error out first
                _log.debug("Added object, keyHolder.getKey() [{}]", keyHolder.getKey());
                object.setId(keyHolder.getKey().longValue());
            } else {
                _log.warn("saveOrUpdate returned a null key, are you use auto_increment is set on the table schema?");
            }
        } else {
            _log.debug("saveOrUpdating updated record");
        }
        try {
            // clear any cached entries related to this object
            clearCache(object);
        } catch (InterruptedException e) {
            _log.warn("clear cache failed with [{}]",e);
        } catch (MemcachedException e) {
            _log.warn("clear cache failed with [{}]",e);
        } catch (TimeoutException e) {
            _log.warn("clear cache failed with [{}]",e);
        }
        // TODO: handle exceptions
        return true;
    }

    protected void setNullableField(PreparedStatement ps, int parameterIndex, long value) throws SQLException {
        if (value == 0) {
            ps.setNull(parameterIndex, java.sql.Types.BIGINT);
        } else {
            ps.setLong(parameterIndex, value);
        }
    }

    protected void setNullableField(PreparedStatement ps, int parameterIndex, Integer value) throws SQLException {
        if (value == null) {
            ps.setNull(parameterIndex, java.sql.Types.INTEGER);
        } else {
            ps.setInt(parameterIndex, value);
        }
    }

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

   /* public void setSyncCache(MemcacheService syncCache) {
        this.syncCache = syncCache;
    }

    public void setAsyncCache(AsyncMemcacheService asyncCache) {
        this.asyncCache = asyncCache;
    }*/

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    
    public JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    public PlatformTransactionManager getTransactionManager() {
        return transactionManager;
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public MemcachedClient getCache() {
        return cache;
    }

    public void setCache(MemcachedClient cache) {
        this.cache = cache;
    }

    public static DateFormat getMysqlDateFormat() {
        return mysqlDateFormat;
    }
}