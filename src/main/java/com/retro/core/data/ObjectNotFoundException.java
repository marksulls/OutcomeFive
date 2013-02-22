package com.retro.core.data;

import java.io.Serializable;

/**
 * @author <a href="mailto:mark@retrocognition.us">Mark Sullivan<a/>
 **/
public class ObjectNotFoundException extends RuntimeException implements Serializable {
    // serializable
    private static final long serialVersionUID = -579191683899968978L;

    public ObjectNotFoundException(String message) {
        super(message);
    }

    public ObjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectNotFoundException(Throwable cause) {
        super(cause);
    }
}