package com.retro.food.web;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.retro.food.core.Menu;

/**
 * operations related to user registration
 * @author mark
 */
@Controller
public class MenuController extends BaseController {
    // logging
    final Logger _log = LoggerFactory.getLogger(MenuController.class);
    
    /**
     * render or change the users password
     */
    @RequestMapping(value="/menu/new",method=RequestMethod.GET)
    public String create(Model model) {
        // create a new menu
        Menu menu = new Menu();
        model.addAttribute("menu",menu);
        // load up the form
        return "menu/create";
    }
    
    /**
     * saves a new registration
     */
    @RequestMapping(value="/menu/new",method=RequestMethod.POST)
    public String save(@Valid Menu menu,BindingResult result,Model model) {
        // check for errors
        if(result.hasErrors()) {
            _log.warn("validation failed with errors - [{}]",result.getAllErrors());
            addErrorMessage("Not all required fields were filled in");
            return "menu/create";
        }
        try {
            // set the cafe id
            menu.setCafeId(getCurrentCafe().getId());
            // save it
            getMenuDao().saveOrUpdate(menu);
        } catch (Exception e) {
            _log.error("error savingm menu [{}] - [{}]",menu,e);
            addErrorMessage("Error creating menu " + menu.getName() + ". Please contact support");
            return "menu/create";
        }
        addSuccessMessage("Successfully created menu " + menu.getName());
        return "/";
    }
}