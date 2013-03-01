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
import com.retro.food.core.Cafe;

public class CafeDao extends BaseDao<Cafe> {
    // logging
    final Logger _log = LoggerFactory.getLogger(CafeDao.class);
    /**
     * Returns the cafes owned by the given athlete
     * 
     * @param athleteId the id of the athlete
     * @return the object, or null if the id is invalid
     */
    public List<Cafe> findOwnedCafesByUserId(long userId) {
        _log.debug("looking up cafes owned by user [{}]",userId);
        
        try {
            // look up the gyms
            return this.jdbcTemplate.query(
                 "select c.* from cafe c " +
                 "inner join cafe_user cu on cu.cafe_id = c.cafe_id " +
                 "inner join user u on cu.user_id = u.user_id " +
                 "where cu.user_id = ? and cu.type > 1",
                 new Object[] {userId}, getRowMapper());
        } catch (EmptyResultDataAccessException e) {
            _log.warn("no cafes found for user [{}]", userId);
        }
        return null;
    }
    
    /**
     * maps the ResultSet to an object
     */
    @Override
    public RowMapper<Cafe> getRowMapper() {
        return new RowMapper<Cafe>() {
            public Cafe mapRow(ResultSet rs, int rowNum) throws SQLException {
                // map result set to object
                Cafe cafe = new Cafe();
                cafe.setId(rs.getLong("cafe_id"));
                cafe.setName(rs.getString("name"));
                cafe.setTimeZone(rs.getString("time_zone"));
                cafe.setCreated(rs.getTimestamp("created"));
                cafe.setUpdated(rs.getTimestamp("updated"));
                // return the objecdt
                return cafe;
            }
        };
    }

    @Override
    public PreparedStatementCreator getSavePreparedStatementCreator(final Cafe cafe) {
        return new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(
                    "insert into cafe (cafe_id,name,time_zone,created,updated) values (?,?,?,now(),now()) "
                    + "on duplicate key update name = values(name),time_zone = values(time_zone),updated = now()", 
                    new String[] { "cafe_id" });
                ps.setLong(1,cafe.getId());
                ps.setString(2,cafe.getName());
                ps.setString(3,cafe.getTimeZone());
                return ps;
            }
        };
    }
}
