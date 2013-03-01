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

import com.google.common.base.Preconditions;
import com.retro.core.data.dao.BaseDao;
import com.retro.food.core.VendorItem;

public class VendorItemDao extends BaseDao<VendorItem> {
    // logging
    final Logger _log = LoggerFactory.getLogger(VendorItemDao.class);
    
    public List<VendorItem> findItemsForVendor(Long vendorId) {
        _log.debug("looking up item owned by vendor [{}]",vendorId);
        
        try {
            // look up the gyms
            return this.jdbcTemplate.query(
                 "select vi.* from vendor_item vi where vi.vendor_id=?",
                 new Object[] {vendorId}, getRowMapper());
        } catch (EmptyResultDataAccessException e) {
            _log.debug("no items found for vendor [{}]", vendorId);
        }
        return null;
    }
    
    /**
     * maps the ResultSet to an object
     */
    @Override
    public RowMapper<VendorItem> getRowMapper() {
        return new RowMapper<VendorItem>() {
            public VendorItem mapRow(ResultSet rs, int rowNum) throws SQLException {
                // map result set to object
                VendorItem item = new VendorItem();
                item.setId(rs.getLong("vendor_item_id"));
                item.setVendorId(rs.getLong("vendor_id"));
                item.setName(rs.getString("name"));
                item.setCategory(rs.getString("category"));
                item.setUpc(rs.getString("upc"));
                item.setSku(rs.getString("sku"));
                item.setDescription(rs.getString("name"));
                item.setMeasureType(rs.getString("measure_type"));
                item.setCount(rs.getInt("count"));
                item.setCost(rs.getBigDecimal("cost"));
                item.setWeight(rs.getBigDecimal("weight_g"));
                item.setWeightDisplay(rs.getString("weight_display"));
                item.setWeightPackage(rs.getString("weight_package"));
                item.setWeightLost(rs.getBigDecimal("weight_lost_g"));
                item.setWeightLostDisplay(rs.getString("weight_lost_display"));
                item.setVolume(rs.getBigDecimal("volume_ml"));
                item.setVolumeDisplay(rs.getString("volume_display"));
                item.setVolumePackage(rs.getString("volume_package"));
                item.setVolumeLost(rs.getBigDecimal("volume_lost_ml"));
                item.setVolumeLostDisplay(rs.getString("volume_lost_display_unit"));
                item.setCreated(rs.getTimestamp("created"));
                item.setUpdated(rs.getTimestamp("updated"));
                // return the objecdt
                return item;
            }
        };
    }

    @Override
    public PreparedStatementCreator getSavePreparedStatementCreator(final VendorItem item) {
        Preconditions.checkNotNull(item,"item cannot be null");
        Preconditions.checkNotNull(item.getVendorId(),"vendor id cannot be null");
        Preconditions.checkNotNull(item.getName(),"name cannot be null");
        Preconditions.checkNotNull(item.getCategory(),"category cannot be null");
        Preconditions.checkNotNull(item.getCost(),"cost cannot be null");
        Preconditions.checkNotNull(item.getMeasureType(),"measure type cannot be null");
        
        return new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO vendor_item (" +
                    "vendor_item_id," + 
                    "vendor_id," + 
                    "name," + 
                    "category," + 
                    "cost," + 
                    "measure_type," + 
                    "sku," + 
                    "upc," + 
                    "description," + 
                    "count," + 
                    "weight_g," + 
                    "weight_display," + 
                    "weight_package," + 
                    "weight_lost_g," + 
                    "weight_lost_display," + 
                    "volume_ml," + 
                    "volume_display," + 
                    "volume_package," + 
                    "volume_lost_ml," + 
                    "volume_lost_display," + 
                    "updated," + 
                    "created)" + 
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),now())" + 
                    "on duplicate key update " +
                    "name=values(name)," + 
                    "category=values(category)," + 
                    "cost=values(cost)," + 
                    "measure_type=values(measure_type)," + 
                    "sku=values(sku)," + 
                    "upc=values(upc)," + 
                    "description=values(description)," + 
                    "count=values(count)," + 
                    "weight_g=values(weight_g)," + 
                    "weight_display=values(weight_display)," + 
                    "weight_package=values(weight_package)," + 
                    "weight_lost_g=values(weight_lost_g)," + 
                    "weight_lost_display=values(weight_lost_display)," + 
                    "volume_ml=values(volume_ml)," + 
                    "volume_display=values(volume_display)," + 
                    "volume_package=values(volume_package)," + 
                    "volume_lost_ml=values(volume_lost_ml)," + 
                    "volume_lost_display=values(volume_lost_display)," + 
                    "updated=now()",
                    new String[] { "vendor_id" });
                // set the placeholders
                ps.setLong(1,item.getId());
                ps.setLong(2,item.getVendorId());
                ps.setString(3,item.getName());
                ps.setString(4,item.getCategory());
                ps.setBigDecimal(5,item.getCost());
                ps.setString(6,item.getMeasureType());
                ps.setString(7,item.getSku());
                ps.setString(8,item.getUpc());
                ps.setObject(9,item.getDescription());
                ps.setObject(10,item.getCount());
                ps.setObject(11,item.getWeight());
                ps.setObject(12,item.getWeightDisplay());
                ps.setObject(13,item.getWeightPackage());
                ps.setObject(14,item.getWeightLost());
                ps.setObject(15,item.getWeightLostDisplay());
                ps.setObject(16,item.getVolume());
                ps.setObject(17,item.getVolumeDisplay());
                ps.setObject(18,item.getVolumePackage());
                ps.setObject(19,item.getVolumeLost());
                ps.setObject(20,item.getVolumeLostDisplay());
                return ps;
            }
        };
    }
}
