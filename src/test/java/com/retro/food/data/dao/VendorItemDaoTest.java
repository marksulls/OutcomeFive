package com.retro.food.data.dao;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.retro.food.TestBaseImpl;
import com.retro.food.core.Cafe;
import com.retro.food.core.Vendor;
import com.retro.food.core.VendorItem;

public class VendorItemDaoTest extends TestBaseImpl {


    @Test
    public final void testFindItemsForVendor() {
        // get a vendor
        Vendor vendor = createVendor();
        getVendorItemDao().findItemsForVendor(vendor.getId());
    }

    @Test
    public final void testCreateWeightItem() {
        Vendor vendor = createVendor();
        VendorItem item = new VendorItem();
        item.setVendorId(vendor.getId());
        item.setName("Vendor Item Name " + rand);
        item.setCategory("produce");
        item.setCost(BigDecimal.valueOf(22.35));
        item.setMeasureType("g");
        item.setWeight(BigDecimal.valueOf(1));
        item.setWeightUnit("lb");
        item.setWeightLost(BigDecimal.valueOf(5));
        item.setWeightLostUnit("oz");
        getVendorItemDao().saveOrUpdate(item);
    }

}
