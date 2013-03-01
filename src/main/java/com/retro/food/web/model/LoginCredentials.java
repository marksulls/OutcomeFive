package com.retro.food.web.model;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * used for login auth
 * @author mark
 */
public class LoginCredentials {
    @Pattern(regexp = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}",message = "Invalid email address.")
    private String email;
    @Size(min = 6,max = 20,message = "The password must be at least 6 characters long.")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginCredentials [email=" + email + ", password=" + password + "]";
    }
}
