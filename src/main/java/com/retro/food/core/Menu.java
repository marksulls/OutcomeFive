package com.retro.food.core;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.retro.core.data.Entity;

/**
 * represents a menu
 * 
 * @author <a href="mailto:mark@retrocognition.us">Mark Sullivan<a/>
 **/
public class Menu extends Entity {
    // serializable
    private static final long serialVersionUID = 4646245132404865570L;

    private Long cafeId;
    @NotNull @NotBlank
    private String name;
    
    public Long getCafeId() {
        return cafeId;
    }
    public void setCafeId(Long cafeId) {
        this.cafeId = cafeId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}