package com.retro.core.data;

import java.util.Iterator;

import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.support.GeneratedKeyHolder;

/**
 * overrides the spring <tt>GeneratedKeyHolder</tt> to accept string type keys
 * coming back from google app engine
 * 
 * @author mark
 */
public class GeneratedStringKeyHolder extends GeneratedKeyHolder {
    /**
     * override to allow string types
     */
    @Override
    public Number getKey() throws InvalidDataAccessApiUsageException, DataRetrievalFailureException {
        if (this.getKeyList().size() == 0) {
            return null;
        }
        if (this.getKeyList().size() > 1 || this.getKeyList().get(0).size() > 1) {
            throw new InvalidDataAccessApiUsageException("The getKey method should only be used when a single key is returned.  " + "The current key entry contains multiple keys: "
                            + this.getKeyList());
        }
        Iterator<Object> keyIter = this.getKeyList().get(0).values().iterator();
        if (keyIter.hasNext()) {
            Object key = keyIter.next();
            if (key instanceof String) {
                key = Long.parseLong((String) key);
            }
            if (!(key instanceof Number)) {
                throw new DataRetrievalFailureException("The generated key is not of a supported numeric type. " + "Unable to cast [" + (key != null ? key.getClass().getName() : null) + "] to ["
                                + Number.class.getName() + "]");
            }
            return (Number) key;
        } else {
            throw new DataRetrievalFailureException("Unable to retrieve the generated key. " + "Check that the table has an identity column enabled.");
        }
    }
}
