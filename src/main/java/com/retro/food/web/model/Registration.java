package com.retro.food.web.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

/**
 * used for registration
 */
public class Registration {
    @NotNull @NotBlank @Pattern(regexp=".+@.+\\.[a-z]+",message="Email is not a valid format")
    private String email;
    @NotNull @NotBlank 
    private String name;
    @NotNull @NotBlank 
    private String password;
    @NotNull @NotBlank 
    private String confirm;
    @NotNull @NotBlank 
    private String cafeName;
    
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getConfirm() {
        return confirm;
    }
    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }
    public String getCafeName() {
        return cafeName;
    }
    public void setCafeName(String cafeName) {
        this.cafeName = cafeName;
    }
}
