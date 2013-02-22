package com.retro.food.security;

import java.io.Serializable;

/**
 * 
 * AuthenticationException 
 *
 * @author <a href="mailto:mark@powerproleasing.com">Mark Sullivan<a/>
 **/
public class AuthenticationException extends Exception implements Serializable {
    // serialize
    private static final long serialVersionUID = -2234619959350025230L;

    public AuthenticationException(String message) {
        super(message);
    }
}
