package com.retro.food.web;

import java.util.Arrays;
import java.util.List;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.retro.core.data.DataException;
import com.retro.core.data.ObjectNotFoundException;
import com.retro.food.core.CafeVendor;
import com.retro.food.core.States;
import com.retro.food.core.Vendor;
import com.retro.food.core.VendorItem;

/**
 * operations related to user vendor
 * @author mark
 */
@Controller
public class VendorController extends BaseController {
    // logging
    final Logger _log = LoggerFactory.getLogger(VendorController.class);
    
    @RequestMapping(value="/vendor/{vendorId}")
    public String view(@PathVariable long vendorId,Model model) throws ObjectNotFoundException {
        _log.debug("viewing vendor [{}]",vendorId);
        // look up the vendor
        Vendor vendor = getVendorDao().getObjectById(vendorId);
        // sanity check
        if(vendor == null) {
            throw new ObjectNotFoundException("no vendor with id [" + vendorId + "]");
        }
        // else store it
        model.addAttribute("vendor",vendor);
        return "vendor/dashboard";
    }
    
    @RequestMapping(value="/vendor/{vendorId}/edit")
    public String edit(@PathVariable long vendorId,Model model) throws ObjectNotFoundException {
        _log.debug("editing vendor [{}]",vendorId);
        // look up the vendor
        Vendor vendor = getVendorDao().getObjectById(vendorId);
        // sanity check
        if(vendor == null) {
            throw new ObjectNotFoundException("no vendor with id [" + vendorId + "]");
        }
        // else store it
        model.addAttribute("vendor",vendor);
        return "vendor/edit";
    }
    
    /**
     * shows form for new vendor
     */
    @RequestMapping(value="/vendor",method=RequestMethod.GET)
    public String createVendor(Model model) {
        // create a new vendor
        Vendor vendor = new Vendor();
        model.addAttribute("vendor",vendor);
        // add the states
        List<States> states = Arrays.asList(States.values());
        model.addAttribute("states",states);
        // load up the form
        return "vendor/edit";
    }
    
    /**
     * saves a new vendor
     */
    @RequestMapping(value="/vendor",method=RequestMethod.POST)
    public String save(@Valid Vendor vendor,BindingResult result,Model model) {
        // check for errors
        if(result.hasErrors()) {
            _log.warn("validation failed with errors - [{}]",result.getAllErrors());
            addErrorMessage("Not all required fields were filled in");
            // add the states
            List<States> states = Arrays.asList(States.values());
            model.addAttribute("states",states);
            return "vendor/edit";
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
            return "vendor/edit";
        }
        addSuccessMessage("Successfully created vendor " + vendor.getName());
        return "redirect:/";
    }
    
    /**
     * shows the form for a new vendor item
     */
    @RequestMapping(value="/vendor/{vendorId}/item",method=RequestMethod.GET)
    public String createVendorItem(@PathVariable long vendorId,Model model) {
        // look up the vendor
        Vendor vendor = getVendorDao().getObjectById(vendorId);
        // sanity check
        if(vendor == null) {
            throw new ObjectNotFoundException("no vendor with id [" + vendorId + "]");
        }
        model.addAttribute("vendor",vendor);
        // create a new vendor
        VendorItem item = new VendorItem();
        model.addAttribute("item",item);
        // load up the form
        return "vendor/edit_item";
    }
    
    /**
     * saves a new vendor item
     */
    @RequestMapping(value="/vendor/{vendorId}/item",method=RequestMethod.POST)
    public String save(@PathVariable long vendorId,@Valid VendorItem item,BindingResult result,Model model) {
        // look up the vendor
        Vendor vendor = getVendorDao().getObjectById(vendorId);
        // sanity check
        if(vendor == null) {
            throw new ObjectNotFoundException("no vendor with id [" + vendorId + "]");
        }
        model.addAttribute("vendor",vendor);
        // check for errors
        if(result.hasErrors()) {
            _log.warn("validation failed with errors - [{}]",result.getAllErrors());
            addErrorMessage("Not all required fields were filled in");
            return "vendor/edit_item";
        }
        try {
            // verify these are set
            item.setVendorId(vendorId);
            // save the vendor
            getVendorItemDao().saveOrUpdate(item);
        } catch (DataException e) {
            _log.error("DataException creating vendtor item [{}] - [{}]",item,e);
            addErrorMessage("Error creating vendor item " + item.getName());
            return "vendor/edit_item";
        } catch (Exception e) {
            _log.error("Exception creating vendtor item [{}] - [{}]",item,e);
            addErrorMessage("Error creating vendor item " + item.getName());
            return "vendor/edit_item";
        }
        addSuccessMessage("Successfully created item " + item.getName());
        return "redirect:/vendor/{vendorId}";
    }
    
    /**
     * deletes a new vendor item
     */
    @RequestMapping(value="/vendor/{vendorId}/item/{vendorItemId}",method=RequestMethod.POST)
    public String deleteVendorItem(@PathVariable long vendorId,@PathVariable long vendorItemId) {
        // look up the vendor
        Vendor vendor = getVendorDao().getObjectById(vendorId);
        // sanity check
        if(vendor == null) {
            throw new ObjectNotFoundException("no vendor with id [" + vendorId + "]");
        }
        // look up the vendor
        VendorItem item = getVendorItemDao().getObjectById(vendorItemId);
        // sanity check
        if(item == null) {
            throw new ObjectNotFoundException("no vendor item with id [" + vendorItemId + "]");
        }
        try {
            // delete the vendor
            getVendorItemDao().deleteObject(item);
        } catch (Exception e) {
            _log.error("error deleting item [{}] - [{}]",item,e);
            addErrorMessage("Error deleted vendor item  " + item.getName());
            return "vendor/edit_item";
        }
        addSuccessMessage("Successfully deleted vendor item " + item.getName());
        return "redirect:/vendor/{vendorId}";
    }
    
    /**
     * deletes a vendor
     */
    @RequestMapping(value="/vendor/{vendorId}/delete",method=RequestMethod.GET)
    public String delete(@PathVariable long vendorId) {
        // look up the vendor
        Vendor vendor = getVendorDao().getObjectById(vendorId);
        // sanity check
        if(vendor == null) {
            throw new ObjectNotFoundException("no vendor with id [" + vendorId + "]");
        }
        try {
            // delete the vendor
            getVendorDao().deleteObject(vendor);
        } catch (Exception e) {
            _log.error("error delete vendor [{}] - [{}]",vendor,e);
            addErrorMessage("Error deleted vendor " + vendor.getName());
            return "vendor/edit_item";
        }
        addSuccessMessage("Successfully deleted vendor " + vendor.getName());
        return "redirect:/";
    }
}