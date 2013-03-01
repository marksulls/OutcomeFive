package com.retro.food.web.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * used for the password change dialog
 */
public class PasswordChange {
    @NotNull @Min(value=1L)
    private Long userId;
    @NotNull
    private String password;
    @NotNull
    private String confirm;
    
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
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
}
