package com.retro.core.data;

import java.io.Serializable;

/**
 * $Id: DataException.java 2880 2009-05-08 18:06:05Z mark $
 * 
 * General <code>Exception</code> class for the platform, this is
 * generally for fatal databased related errors
 *
 * $Log$
 * @author <a href="mailto:mark@safetyweb.com">Mark Sullivan<a/>
 * @version $Revision$
 **/
public class DataException extends RuntimeException implements Serializable {

	/* serializable */
    private static final long serialVersionUID = -3431457912999806952L;

    public DataException(String message) {
        super(message);
    }

    public DataException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataException(Throwable cause) {
        super(cause);
    }
}