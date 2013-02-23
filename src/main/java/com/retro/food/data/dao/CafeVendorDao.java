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
import com.retro.food.core.CafeVendor;

public class CafeVendorDao extends BaseDao<CafeVendor> {
    // logging
    final Logger _log = LoggerFactory.getLogger(CafeVendorDao.class);
    
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
    public String getObjectCacheKey(CafeVendor object) {
        return new StringBuilder("cafe_vendor_id_").append(object.getVendorId()).append("_").append(object.getCafeId()).toString();
    }
    
    /**
     * maps the ResultSet to an object
     */
    @Override
    public RowMapper<CafeVendor> getRowMapper() {
        return new RowMapper<CafeVendor>() {
            public CafeVendor mapRow(ResultSet rs, int rowNum) throws SQLException {
                // map result set to object
                CafeVendor cafe = new CafeVendor();
                cafe.setCafeId(rs.getLong("cafe_id"));
                cafe.setVendorId(rs.getLong("vendor_id"));
                cafe.setCreated(rs.getTimestamp("created"));
                cafe.setUpdated(rs.getTimestamp("updated"));
                // return the object
                return cafe;
            }
        };
    }

    @Override
    public PreparedStatementCreator getSavePreparedStatementCreator(final CafeVendor object) {
        return new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(
                    "insert into cafe_vendor (vendor_id,cafe_id,created,updated) values (?,?,now(),now()) "
                    + "on duplicate key update updated = now()");
                ps.setLong(1,object.getVendorId());
                ps.setLong(2,object.getCafeId());
                return ps;
            }
        };
    }
}
