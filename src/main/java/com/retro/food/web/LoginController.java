package com.retro.food.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.retro.food.web.model.LoginCredentials;


@Controller
public class LoginController extends BaseController {
    final Logger _log = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String login(Model model) {
        _log.debug("Entering login");
        model.addAttribute("credentials", new LoginCredentials());
        return "login";
    }
    
    /**
     * this is here as a workaround to display the login failed message. 
     * there is probably a better way to do this in the spring security world
     */
    @RequestMapping(value="/login/failure", method = RequestMethod.GET)
    public String loginFailed(Model model) {
        _log.debug("entering login failed");
//        HttpServletRequest curRequest = 
//                        ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
//                        .getRequest();
//        _log.info("[{}]",curRequest);
//        // get the session
//        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//        HttpSession session = attr.getRequest().getSession();
//        Enumeration keys = session.getAttributeNames();
//        while(keys.hasMoreElements()) {
//            String key = (String)keys.nextElement();
//            _log.info("session value is [{}] = [{}]",key,session.getAttribute(key));
//        }
        // add an error message
        addErrorMessage("Invalid email or password. Please try again.");
        model.addAttribute("credentials", new LoginCredentials());
        return "login";
    }
}

