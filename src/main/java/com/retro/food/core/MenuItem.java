package com.retro.food.core;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.ibm.icu.util.TimeZone;
import com.retro.core.data.Entity;
import com.retro.core.util.DateUtil;

/**
 * represents a cafe
 * 
 * @author <a href="mailto:mark@retrocognition.us">Mark Sullivan<a/>
 **/
public class MenuItem extends Entity {
    // serializable
    private static final long serialVersionUID = -4601576219551201071L;
    @NotNull
    private String name;
    private MenuCategory category;
    private BigDecimal cost;
    // BigDecimal.ROUND_HALF_EVEN
    private List<Ingredient> ingredients;
}