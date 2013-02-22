package com.retro.core.data;

import java.io.Serializable;

/**
 * @author <a href="mailto:mark@retrocognition.us">Mark Sullivan<a/>
 **/
public class ObjectNotFoundException extends RuntimeException implements Serializable {

    // serializable
    private static final long serialVersionUID = 9217472920026414526L;

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