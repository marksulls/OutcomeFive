package com.retro.food.web;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.retro.food.core.CafeVendor;
import com.retro.food.core.Vendor;

/**
 * operations related to user vendor
 * @author mark
 */
@Controller
public class VendorController extends BaseController {
    // logging
    final Logger _log = LoggerFactory.getLogger(VendorController.class);
    
    /**
     * render or change the users password
     */
    @RequestMapping(value="/vendor/new",method=RequestMethod.GET)
    public String create(Model model) {
        // create a new vendor
        Vendor vendor = new Vendor();
        model.addAttribute("vendor",vendor);
        // load up the form
        return "vendor/create";
    }
    
    /**
     * saves a new vendor
     */
    @RequestMapping(value="/vendor/new",method=RequestMethod.POST)
    public String save(@Valid Vendor vendor,BindingResult result,Model model) {
        // check for errors
        if(result.hasErrors()) {
            _log.warn("validation failed with errors - [{}]",result.getAllErrors());
            addErrorMessage("Not all required fields were filled in");
            return "vendor/create";
        }
        // do this in a transaction
        PlatformTransactionManager transactionManager = getUserDao().getTransactionManager();
        // get the connection
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
         // explicitly setting the transaction name 
        def.setName("CVTX");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        // start the transaction
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            // save the vendor
            getVendorDao().saveOrUpdate(vendor);
            // create a mapping
            CafeVendor cafeVendor = new CafeVendor();
            cafeVendor.setCafeId(getCurrentCafe().getId());
            cafeVendor.setVendorId(vendor.getId());
            getCafeVendorDao().saveOrUpdate(cafeVendor);
            // commit the transaction
            transactionManager.commit(status);
        } catch (Exception e) {
            _log.error("error registering [{}] - [{}]",vendor,e);
            transactionManager.rollback(status);
            addErrorMessage("Error creating vendor " + vendor.getName());
            return "vendor/create";
        }
        addSuccessMessage("Successfully created vendor " + vendor.getName());
        return "redirect:/";
    }
}