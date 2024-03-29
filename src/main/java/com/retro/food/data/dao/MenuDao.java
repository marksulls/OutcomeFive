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
import com.retro.food.core.Menu;

public class MenuDao extends BaseDao<Menu> {
    // logging
    final Logger _log = LoggerFactory.getLogger(MenuDao.class);
    
    public List<Menu> findMenusForCafes(Long cafeId) {
        _log.debug("finding menus for cafe [{}]",cafeId);
        try {
            // look up the gyms
            return this.jdbcTemplate.query(
                 "select * from menu where cafe_id=?",
                 new Object[] {cafeId},getRowMapper());
        } catch (EmptyResultDataAccessException e) {
            _log.warn("no menus found for user [{}]",cafeId);
        }
        return null;
    }
    
    /**
     * maps the ResultSet to an object
     */
    @Override
    public RowMapper<Menu> getRowMapper() {
        return new RowMapper<Menu>() {
            public Menu mapRow(ResultSet rs, int rowNum) throws SQLException {
                // map result set to object
                Menu menu = new Menu();
                menu.setId(rs.getLong("menu_id"));
                menu.setCafeId(rs.getLong("cafe_id"));
                menu.setName(rs.getString("name"));
                menu.setCreated(rs.getTimestamp("created"));
                menu.setUpdated(rs.getTimestamp("updated"));
                // return the objecdt
                return menu;
            }
        };
    }

    @Override
    public PreparedStatementCreator getSavePreparedStatementCreator(final Menu menu) {
        return new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(
                    "insert into menu (menu_id,cafe_id,name,created,updated) values (?,?,?,now(),now()) "
                    + "on duplicate key update name = values(name),updated = now()", 
                    new String[] { "menu_id" });
                ps.setLong(1,menu.getId());
                ps.setLong(2,menu.getCafeId());
                ps.setString(3,menu.getName());
                return ps;
            }
        };
    }
}
