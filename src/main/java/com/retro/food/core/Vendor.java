package com.retro.food.core;

import javax.validation.constraints.NotNull;

import com.retro.core.data.Entity;

/**
 * represents a vendor
 * 
 * @author <a href="mailto:mark@retrocognition.us">Mark Sullivan<a/>
 **/
public class Vendor extends Entity {
    // serializable
    private static final long serialVersionUID = -4871084067736798667L;
    @NotNull
    private String name;
}