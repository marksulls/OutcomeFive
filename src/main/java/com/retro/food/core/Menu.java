package com.retro.food.core;

import javax.validation.constraints.NotNull;

import com.ibm.icu.util.TimeZone;
import com.retro.core.data.Entity;
import com.retro.core.util.DateUtil;

/**
 * represents a cafe
 * 
 * @author <a href="mailto:mark@retrocognition.us">Mark Sullivan<a/>
 **/
public class Menu extends Entity {
    // serializable
    private static final long serialVersionUID = 6458323562874908412L;
    @NotNull
    private String name;
    @NotNull
    private String timeZone;
    
    // helper
    public String getTimeZoneOffset() {
        TimeZone tz = TimeZone.getTimeZone(getTimeZone());
        // sanity check
        if(tz != null) {
            // get the offset
            return DateUtil.getOffsetString(tz);
        } 
        // else
        return "Bad Timezone";
    }

    public String getTimeZone() {
        return timeZone;
    }
    
    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}