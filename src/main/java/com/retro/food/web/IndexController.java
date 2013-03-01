package com.retro.food.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.retro.core.data.ObjectNotFoundException;
import com.retro.food.core.Cafe;
import com.retro.food.core.Menu;
import com.retro.food.core.User;
import com.retro.food.core.Vendor;

@Controller
public class IndexController extends BaseController {
    // logging
    final Logger _log = LoggerFactory.getLogger(IndexController.class);
    
    @RequestMapping({"/cafe/{cafeId}/dashboard"})
    public String index(@PathVariable long cafeId,Model model,SecurityContextHolderAwareRequestWrapper wrapper) {
        // get the current cafe
        Cafe cafe = getCafeDao().getObjectById(cafeId);
        // sanity check
        if(cafe == null) {
            throw new ObjectNotFoundException("no cafe with id [" + cafeId + "]");
        }
        // store the cafe
        model.addAttribute("cafe",cafe);
        // look up the menus
        List<Menu> menus = getMenuDao().findMenusForCafes(cafe.getId());
        model.addAttribute("menus",menus);
        List<Vendor> vendors = getVendorDao().findVendorsForCafe(cafe.getId());
        model.addAttribute("vendors",vendors);
        return "dashboard";
    }
    
    @RequestMapping(value={"","/"})
    public String index(Model model,SecurityContextHolderAwareRequestWrapper wrapper) {
        // check for a user
        User user = getCurrentUser();
        // if null, send to landing
        if(user == null) {
            _log.debug("current user is null",user);
            return "index";
        }
        // get the cafe
        Cafe cafe = getCafeDao().getObjectById(user.getCafeId());
        // this should never happen
        if(cafe == null) {
             cafe = user.getCafesOwned().get(0);
        }
        // else show the dashboard
        _log.debug("viewing cafe [{}]",cafe);
        return index(cafe.getId(),model,wrapper);
    }
}