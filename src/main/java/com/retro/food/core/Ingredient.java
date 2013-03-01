package com.retro.food.core;

import javax.validation.constraints.NotNull;

import com.ibm.icu.util.TimeZone;
import com.retro.core.data.Entity;
import com.retro.core.util.DateUtil;

/**
 * represents an ingredient in an item
 * 
 * @author <a href="mailto:mark@retrocognition.us">Mark Sullivan<a/>
 **/
public class Ingredient extends Entity {
    // serializable
    private static final long serialVersionUID = 6632059734227203002L;
    @NotNull
    private String name;
    private WeightUnit weightUnit;
    private double weight;
    private VolumeUnit volumeUnit;
    private double volume;
}