package com.retro.food.core;

import com.retro.core.data.Entity;

/**
 * represents a vendor to cafe mapping
 * 
 * @author <a href="mailto:mark@retrocognition.us">Mark Sullivan<a/>
 **/
public class CafeVendor extends Entity {
    // serializable
    private static final long serialVersionUID = 2413078713984449124L;
    
    private Long cafeId;
    private Long vendorId;
    
    public Long getCafeId() {
        return cafeId;
    }
    public void setCafeId(Long cafeId) {
        this.cafeId = cafeId;
    }
    public Long getVendorId() {
        return vendorId;
    }
    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }
}