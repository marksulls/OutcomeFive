package com.retro.food.data.dao;

/**
 * this is a work around for the json serializers
 * @author mark
 */
public class DaoFactory extends DaoKeeper {
    // singleton instance of this class
    private static DaoFactory instance = null;

    private DaoFactory() {
        // Exists only to defeat instantiation.
    }

    // returns the instance of this singleton
    public static DaoFactory getInstance() {
        if (instance == null) {
            instance = new DaoFactory();
        }
        return instance;
    }
}
