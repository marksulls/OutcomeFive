package com.retro.food;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.retro.food.core.Cafe;
import com.retro.food.core.Vendor;
import com.retro.food.data.dao.CafeDao;
import com.retro.food.data.dao.DaoFactory;
import com.retro.food.data.dao.UserDao;
import com.retro.food.data.dao.VendorDao;
import com.retro.food.data.dao.VendorItemDao;

public class TestBaseImpl {
 // logging
    final static Logger _log = LoggerFactory.getLogger(TestBaseImpl.class);
    private static ApplicationContext ctx = null;
    private static DaoFactory daoFactory;
    
    // generate a random number
    public static String rand = RandomStringUtils.randomAlphanumeric(8);
    
    private static Cafe cafe;
    private static Vendor vendor;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        _log.info("spinning up spring context");
        // init spring
        ctx = new FileSystemXmlApplicationContext(new String[] {"war/WEB-INF/dispatcher-servlet.xml"});
        setDaoFactory(ctx.getBean("daoFactorySingleton",DaoFactory.class));
    }
    
    protected CafeDao getCafeDao() {
        return getCtx().getBean("cafeDao",CafeDao.class);
    }
    protected UserDao getUserDao() {
        return getCtx().getBean("userDao",UserDao.class);
    }
    protected VendorDao getVendorDao() {
        return getCtx().getBean("vendorDao",VendorDao.class);
    }
    protected VendorItemDao getVendorItemDao() {
        return getCtx().getBean("vendorItemDao",VendorItemDao.class);
    }
    
    public static Cafe createCafe() {
     // lazy load
        if(cafe == null) {
            // create a new gym
            cafe = new Cafe();
            cafe.setName("Test Cafe " + rand);
            cafe.setTimeZone("America/Denver");
            // save it
            getDaoFactory().getCafeDao().saveOrUpdate(cafe);
        }
        // return it
        return cafe;
    }
    public static Vendor createVendor() {
        // lazy load
        if(vendor == null) {
            // create a new gym
            vendor = new Vendor();
            vendor.setName("Test Vendor " + rand);
            // save it
            getDaoFactory().getVendorDao().saveOrUpdate(vendor);
        }
        // return it
        return vendor;
    }
    
    public ApplicationContext getCtx() {
        return ctx;
    }

    public void setCtx(ApplicationContext ctx) {
        TestBaseImpl.ctx = ctx;
    }

    public static DaoFactory getDaoFactory() {
        return daoFactory;
    }

    public static void setDaoFactory(DaoFactory daoFactory) {
        TestBaseImpl.daoFactory = daoFactory;
    }
}
