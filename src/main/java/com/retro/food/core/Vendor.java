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
    private String street1;
    private String street2;
    private String street3;
    private String city;
    private String state;
    private String zip;
    private String country;
    private String phone;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getStreet1() {
        return street1;
    }
    public void setStreet1(String street1) {
        this.street1 = street1;
    }
    public String getStreet2() {
        return street2;
    }
    public void setStreet2(String street2) {
        this.street2 = street2;
    }
    public String getStreet3() {
        return street3;
    }
    public void setStreet3(String street3) {
        this.street3 = street3;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getZip() {
        return zip;
    }
    public void setZip(String zip) {
        this.zip = zip;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
}