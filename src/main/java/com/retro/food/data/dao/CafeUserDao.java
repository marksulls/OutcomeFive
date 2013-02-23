package com.retro.food.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

import com.retro.core.data.dao.BaseDao;
import com.retro.food.core.CafeUser;

public class CafeUserDao extends BaseDao<CafeUser> {
    // logging
    final Logger _log = LoggerFactory.getLogger(CafeUserDao.class);
    
   // no auto_increment on this table
    public boolean hasAutoKey() {
        return false;
    }
    
    /**
     * Creates a cache key for the objectId. Defaults to tableName_[objectId].
     * 
     * @param objectId
     * @return
     */
    @Override
    public String getObjectCacheKey(CafeUser object) {
        return new StringBuilder("cafe_user_id_").append(object.getUserId()).append("__").append(object.getCafeId()).toString();
    }
    
    /**
     * maps the ResultSet to an object
     */
    @Override
    public RowMapper<CafeUser> getRowMapper() {
        return new RowMapper<CafeUser>() {
            public CafeUser mapRow(ResultSet rs, int rowNum) throws SQLException {
                // map result set to object
                CafeUser cafe = new CafeUser();
                cafe.setCafeId(rs.getLong("cafe_id"));
                cafe.setUserId(rs.getLong("user_id"));
                cafe.setType(rs.getInt("type"));
                cafe.setCreated(rs.getTimestamp("created"));
                cafe.setUpdated(rs.getTimestamp("updated"));
                // return the object
                return cafe;
            }
        };
    }

    @Override
    public PreparedStatementCreator getSavePreparedStatementCreator(final CafeUser object) {
        return new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(
                    "insert into cafe_user (user_id,cafe_id,type,created,updated) values (?,?,?,now(),now()) "
                    + "on duplicate key update type = values(type),updated = now()");
                ps.setLong(1,object.getUserId());
                ps.setLong(2,object.getCafeId());
                ps.setInt(3,object.getType());
                return ps;
            }
        };
    }
}
