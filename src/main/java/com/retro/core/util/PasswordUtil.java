package com.retro.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

public class PasswordUtil {
    // logging
    final static Logger _log = LoggerFactory.getLogger(PasswordUtil.class);
    // encoder, i hope to god this is thread safe
    private static ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
    // the salt, never change this otherwise no one's password will work
    private static final String SALT = "nasdrq90341234qkalsdfs";
   
    /**
     * encypts the given password
     * @param athlete
     * @param password
     * @return the encrypted password
     */
    public static String encryptPassword(final String password) {
        // encrypt the password
        return encoder.encodePassword(password,SALT);
    }
}
