package com.retro.food.data.dao;

/**
 * this is here to just hold all the dao objects
 * @author mark
 */
public class DaoKeeper {
    // data
    private CafeDao cafeDao;
    private CafeUserDao cafeUserDao;
    private CafeVendorDao cafeVendorDao;
    private MenuDao menuDao;
    private MenuItemDao menuItemDao;
    private UserDao userDao;
    private VendorDao vendorDao;
    private VendorItemDao vendorItemDao;
    
    public CafeDao getCafeDao() {
        return cafeDao;
    }
    public void setCafeDao(CafeDao cafeDao) {
        this.cafeDao = cafeDao;
    }
    public CafeUserDao getCafeUserDao() {
        return cafeUserDao;
    }
    public void setCafeUserDao(CafeUserDao cafeUserDao) {
        this.cafeUserDao = cafeUserDao;
    }
    public CafeVendorDao getCafeVendorDao() {
        return cafeVendorDao;
    }
    public void setCafeVendorDao(CafeVendorDao cafeVendorDao) {
        this.cafeVendorDao = cafeVendorDao;
    }
    public MenuDao getMenuDao() {
        return menuDao;
    }
    public void setMenuDao(MenuDao menuDao) {
        this.menuDao = menuDao;
    }
    public MenuItemDao getMenuItemDao() {
        return menuItemDao;
    }
    public void setMenuItemDao(MenuItemDao menuItemDao) {
        this.menuItemDao = menuItemDao;
    }
    public UserDao getUserDao() {
        return userDao;
    }
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
    public VendorDao getVendorDao() {
        return vendorDao;
    }
    public void setVendorDao(VendorDao vendorDao) {
        this.vendorDao = vendorDao;
    }
    public VendorItemDao getVendorItemDao() {
        return vendorItemDao;
    }
    public void setVendorItemDao(VendorItemDao vendorItemDao) {
        this.vendorItemDao = vendorItemDao;
    }
}
