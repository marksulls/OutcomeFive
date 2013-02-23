package com.retro.food.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

import com.retro.core.data.dao.BaseDao;
import com.retro.food.core.Vendor;

public class VendorDao extends BaseDao<Vendor> {
    // logging
    final Logger _log = LoggerFactory.getLogger(VendorDao.class);
    /**
     * Returns the vendors owned by the given athlete
     * 
     * @param athleteId the id of the athlete
     * @return the object, or null if the id is invalid
     */
    public List<Vendor> findVendorsForCafe(Long cafeId) {
        _log.debug("looking up vendors by cafe [{}]",cafeId);
        
        try {
            // look up the gyms
            return this.jdbcTemplate.query(
                 "select v.* from vendor v " +
                 "inner join cafe_vendor cv on cv.vendor_id = v.vendor_id " +
                 "inner join cafe c on cv.cafe_id = c.cafe_id " +
                 "where cv.cafe_id = ? ",
                 new Object[] {cafeId}, getRowMapper());
        } catch (EmptyResultDataAccessException e) {
            _log.warn("no vendors found for cafe [{}]", cafeId);
        }
        return null;
    }
    
    /**
     * maps the ResultSet to an object
     */
    @Override
    public RowMapper<Vendor> getRowMapper() {
        return new RowMapper<Vendor>() {
            public Vendor mapRow(ResultSet rs, int rowNum) throws SQLException {
                // map result set to object
                Vendor vendor = new Vendor();
                vendor.setId(rs.getLong("vendor_id"));
                vendor.setName(rs.getString("name"));
                vendor.setStreet1(rs.getString("street1"));
                vendor.setStreet2(rs.getString("street2"));
                vendor.setStreet3(rs.getString("street3"));
                vendor.setCity(rs.getString("city"));
                vendor.setState(rs.getString("state"));
                vendor.setZip(rs.getString("zip"));
                vendor.setCountry(rs.getString("country"));
                vendor.setPhone(rs.getString("phone"));
                vendor.setCreated(rs.getTimestamp("created"));
                vendor.setUpdated(rs.getTimestamp("updated"));
                // return the objecdt
                return vendor;
            }
        };
    }

    @Override
    public PreparedStatementCreator getSavePreparedStatementCreator(final Vendor object) {
        return new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(
                    "insert into vendor (vendor_id,name,street1,street2,street3,city,state,zip,phone,created,updated) values (?,?,?,?,?,?,?,?,?,now(),now()) " +
                    "on duplicate key update " +
                    "name = values(name)," +
                    "street1 = values(street1), " +
                    "street2 = values(street2), " +
                    "street3 = values(street3), " +
                    "city = values(city), " +
                    "state = values(state), " +
                    "zip = values(zip), " +
                    "phone = values(phone)," +
                    "updated = now()", 
                    new String[] { "vendor_id" });
                ps.setLong(1,object.getId());
                ps.setString(2,object.getName());
                ps.setString(3, object.getStreet1());
                ps.setString(4, object.getStreet2());
                ps.setString(5, object.getStreet3());
                ps.setString(6, object.getCity());
                ps.setString(7, object.getState());
                ps.setString(8, object.getZip());
                ps.setString(9, object.getPhone());
                return ps;
            }
        };
    }
}
