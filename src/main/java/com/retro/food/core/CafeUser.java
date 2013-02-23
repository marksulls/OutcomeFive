package com.retro.food.core;

import com.retro.core.data.Entity;

/**
 * represents a user to cafe mapping
 * 
 * @author <a href="mailto:mark@retrocognition.us">Mark Sullivan<a/>
 **/
public class CafeUser extends Entity {
    // serializable
    private static final long serialVersionUID = -807685525609439269L;
    
    private Long cafeId;
    private Long userId;
    private int type;
    
    public Long getCafeId() {
        return cafeId;
    }
    public void setCafeId(Long cafeId) {
        this.cafeId = cafeId;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
}