package com.retro.food.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

import com.retro.core.data.dao.BaseDao;
import com.retro.food.core.User;

public class UserDao extends BaseDao<User> {
    // logging
    final Logger _log = LoggerFactory.getLogger(UserDao.class);
    
    /**
     * Returns the user with the given email
     * 
     * @param email the email of the athlete
     * @return the object, or null if the id is invalid
     */
    public User getUserByEmail(String email) {
        _log.debug("looking up user by email [{}]",email);
        try {
            // look up the object
            return this.jdbcTemplate.queryForObject(
                    "select * from user where email = ?",
                    new Object[] {email}, getRowMapper());
        } catch (EmptyResultDataAccessException e) {
            _log.debug("no user found for email [{}]", email);
        } catch (DataAccessException e) {
            _log.error("error looking up user with email [{}] - [{}]",email,e);
        }
        return null;
    }
    /**
     * maps the ResultSet to an object
     */
    @Override
    public RowMapper<User> getRowMapper() {
        return new RowMapper<User>() {
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                // map result set to object
                User user = new User();
                user.setId(rs.getLong("user_id"));
                user.setCafeId(rs.getLong("cafe_id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setCreated(rs.getTimestamp("created"));
                user.setUpdated(rs.getTimestamp("updated"));
                // return the object
                return user;
            }
        };
    }

    @Override
    public PreparedStatementCreator getSavePreparedStatementCreator(final User user) {
        return new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(
                    "insert into user (user_id,cafe_id,name,email,password,created,updated) values (?,?,?,?,?,now(),now()) " +
                    "on duplicate key update " +
                    "name = values(name)," +
                    "password = values(password)," +
                    "email = values(email)," +
                    "updated = now()", 
                    new String[] { "user_id" });
                ps.setLong(1,user.getId());
                ps.setLong(2,user.getCafeId());
                ps.setString(3,user.getName());
                ps.setString(4,user.getEmail());
                ps.setString(5,user.getPassword());
                return ps;
            }
        };
    }
}
