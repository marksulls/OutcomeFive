package com.retro.core.data;

import java.io.Serializable;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.sql.Timestamp;

/**
 * $Id$
 * 
 * base class for all objects stored in the database
 */
@SuppressWarnings("serial")
public abstract class Entity implements Serializable {
    public static final Long NULL = 0L;
    // used to decide whether or not this class has been persisted
    // this needs to be 0 for mysql to be cool 
    private Long id = Entity.NULL;
    // meta data
    private Timestamp created;
    private Timestamp updated;
    // active or inactive
    private boolean active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the created
     */
    public Timestamp getCreated() {
        return created;
    }

    /**
     * @param created
     *            the created to set
     */
    public void setCreated(Timestamp created) {
        this.created = created;
    }


    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * generic <tt>Object.toString()</tt> implementation
     */
    @SuppressWarnings("rawtypes")
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String newLine = System.getProperty("line.separator");
        result.append(this.getClass().getName());
        result.append(" {");
        result.append(newLine);
        // determine fields declared in this class only (no fields of
        // superclass)
        Class clazz = this.getClass();
        do {
            Field[] fields = clazz.getDeclaredFields();
            // requires access to private field:
            AccessibleObject.setAccessible(fields, true);
            // print field names paired with their values
            for (Field field : fields) {
                result.append("  ");
                try {
                    result.append(field.getName());
                    result.append(": ");
                    if(field.getType().equals(Long.TYPE)) {
                        result.append(Long.toString(field.getLong(this)));
                    } else {
                        result.append(field.get(this));
                    }
                } catch (IllegalAccessException ex) {
                    System.err.println(ex);
                }
                result.append(newLine);
            }
            clazz = clazz.getSuperclass();
        } while (clazz != null && clazz != Object.class);

        result.append("}");

        return result.toString();
    }
}
