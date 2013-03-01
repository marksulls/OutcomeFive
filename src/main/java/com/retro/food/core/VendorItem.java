package com.retro.food.core;

import java.math.BigDecimal;

import com.retro.core.data.Entity;

/**
 * represents an item sold by a vendor
 * 
 * @author <a href="mailto:mark@retrocognition.us">Mark Sullivan<a/>
 **/
public class VendorItem extends Entity {
    // serializable
    private static final long serialVersionUID = 2499951840687460093L;
    private Long vendorId;
    private String name;
    private String category;
    private String upc;
    private String sku;
    private String description;
    private BigDecimal cost;
    private Integer count;
    private BigDecimal weight;
    private String weightUnit;
    private String weightDisplay;
    private String weightPackage;
    private BigDecimal weightLost;
    private String weightLostUnit;
    private String weightLostDisplay;
    private BigDecimal volume;
    private String volumeUnit;
    private String volumeDisplay;
    private String volumePackage;
    private BigDecimal volumeLost;
    private String volumeLostUnit;
    private String volumeLostDisplay;
    private String measureType;
    
    public Long getVendorId() {
        return vendorId;
    }
    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getUpc() {
        return upc;
    }
    public void setUpc(String upc) {
        this.upc = upc;
    }
    public String getSku() {
        return sku;
    }
    public void setSku(String sku) {
        this.sku = sku;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public BigDecimal getCost() {
        return cost;
    }
    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
    public Integer getCount() {
        return count;
    }
    public void setCount(Integer count) {
        this.count = count;
    }
    public BigDecimal getWeight() {
        return weight;
    }
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }
    public String getWeightUnit() {
        return weightUnit;
    }
    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit;
    }
    public String getWeightDisplay() {
        return weightDisplay;
    }
    public void setWeightDisplay(String weightDisplay) {
        this.weightDisplay = weightDisplay;
    }
    public String getWeightPackage() {
        return weightPackage;
    }
    public void setWeightPackage(String weightPackage) {
        this.weightPackage = weightPackage;
    }
    public BigDecimal getWeightLost() {
        return weightLost;
    }
    public void setWeightLost(BigDecimal weightLost) {
        this.weightLost = weightLost;
    }
    public String getWeightLostUnit() {
        return weightLostUnit;
    }
    public void setWeightLostUnit(String weightLostUnit) {
        this.weightLostUnit = weightLostUnit;
    }
    public String getWeightLostDisplay() {
        return weightLostDisplay;
    }
    public void setWeightLostDisplay(String weightLostDisplay) {
        this.weightLostDisplay = weightLostDisplay;
    }
    public BigDecimal getVolume() {
        return volume;
    }
    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }
    public String getVolumeUnit() {
        return volumeUnit;
    }
    public void setVolumeUnit(String volumeUnit) {
        this.volumeUnit = volumeUnit;
    }
    public String getVolumeDisplay() {
        return volumeDisplay;
    }
    public void setVolumeDisplay(String volumeDisplay) {
        this.volumeDisplay = volumeDisplay;
    }
    public String getVolumePackage() {
        return volumePackage;
    }
    public void setVolumePackage(String volumePackage) {
        this.volumePackage = volumePackage;
    }
    public BigDecimal getVolumeLost() {
        return volumeLost;
    }
    public void setVolumeLost(BigDecimal volumeLost) {
        this.volumeLost = volumeLost;
    }
    public String getVolumeLostUnit() {
        return volumeLostUnit;
    }
    public void setVolumeLostUnit(String volumeLostUnit) {
        this.volumeLostUnit = volumeLostUnit;
    }
    public String getVolumeLostDisplay() {
        return volumeLostDisplay;
    }
    public void setVolumeLostDisplay(String volumeLostDisplay) {
        this.volumeLostDisplay = volumeLostDisplay;
    }
    public String getMeasureType() {
        return measureType;
    }
    public void setMeasureType(String measureType) {
        this.measureType = measureType;
    }
}