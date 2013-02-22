package com.retro.food.data.dao;

/**
 * this is here to just hold all the dao objects
 * @author mark
 */
public class DaoKeeper {
    // data
    private CafeDao cafeDao;
    private UserDao userDao;

    public CafeDao getCafeDao() {
        return cafeDao;
    }
    public void setCafeDao(CafeDao cafeDao) {
        this.cafeDao = cafeDao;
    }
    public UserDao getUserDao() {
        return userDao;
    }
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
