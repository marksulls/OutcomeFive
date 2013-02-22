package com.retro.food.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.retro.core.data.ObjectNotFoundException;
import com.retro.food.core.Cafe;

@Controller
@RequestMapping("/")
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
        // store them
        model.addAttribute("cafe",cafe);
        return "dashboard";
    }
    
    @RequestMapping({"/","/index"})
    public String index(Model model,SecurityContextHolderAwareRequestWrapper wrapper) {
        // get the cafe
        Cafe cafe = getCurrentCafe();
        // if null, send to landing
        if(cafe == null) {
            _log.info("current athlete is null",cafe);
            return "index";
        }
        // else show the dashboard
        _log.info("viewing cafe [{}]",cafe);
        return index(cafe.getId(),model,wrapper);
    }
}