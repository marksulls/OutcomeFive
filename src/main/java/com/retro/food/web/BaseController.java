package com.retro.food.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.retro.core.data.ObjectNotFoundException;
import com.retro.food.core.Cafe;
import com.retro.food.core.User;
import com.retro.food.data.dao.DaoKeeper;

/**
 * base location for controllers
 * @author mark
 */
public abstract class BaseController extends DaoKeeper {
    final Logger _log = LoggerFactory.getLogger(BaseController.class);
    
    /**
     * helper to grab the session
     * @return
     */
    private HttpSession getSession() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession();
    }
    
    public void setCurrentCafeId(Long cafeId) {
        getSession().setAttribute("currentCafeId",cafeId);
    }
    
    // helpers to get current cafe
    public Long getCurrentCafeId() {
        Object id = getSession().getAttribute("currentCafeId");
        if (id != null) {
            return (Long) id;
        }
        return null;
    }

    public Cafe getCurrentCafe() {
        Long cafeId = getCurrentCafeId();
        if(cafeId != null) {
            return getCafeDao().getObjectById(cafeId);
        }
        return null;
    }
    
    /**
     * Retrieves the current athlete from the principal object
     * @return
     */
    public User getCurrentUser() {
        // get the security principal (should be an athlete)
        // TODO: sanity check this
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        _log.debug("contexts holder is [{}]",user);
        return user;
    }
    
    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public void handleObjectNotFoundException(ObjectNotFoundException e,HttpServletResponse response) {
        _log.error("caught an ObjectNotFoundException - [{}]",e);
    }
    
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public void handleMissingServletRequestParameterException(MissingServletRequestParameterException e,HttpServletResponse response) {
        _log.error("caught an MissingServletRequestParameterException - [{}]",e);
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGeneralException(Exception e,HttpServletResponse response) {
        _log.error("caught an Exception - [{}]",e);
        return "error500";
    }
    
    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(AuthenticationCredentialsNotFoundException e,HttpServletResponse response) {
        _log.error("caught an AuthenticationCredentialsNotFoundException - [{}]",e);
        // need to login, set a message
        addWarningMessage("Please login to continue");
        return "redirect:/login";
    }
    
    /**
     * needed to fix a bug in json request
     * https://jira.springsource.org/browse/SPR-9310
     * 
     * @param e
     * @param response
     * @return
     */
    @ExceptionHandler(BindException.class)
    public String handleBindException(BindException e,HttpServletResponse response) {
        _log.error("caught a BindException - [{}]",e);
        return "error500";
    }

    /**
     * helper to set error messages on the request
     */
    @SuppressWarnings("unchecked")
    public void addErrorMessage(String message) {
        HttpServletRequest curRequest = 
                        ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                        .getRequest();
        // get the output flash map
        FlashMap map = RequestContextUtils.getOutputFlashMap(curRequest);
        List<String> messages;
        // look for current errors
        if (map.containsKey("_errors")) {
            messages = (List<String>) map.get("_errors");
        } else {
            // create a new one
            messages = new ArrayList<String>();
            // attach it
            map.put("_errors",messages);
        }
        // add the message to the flash map
        messages.add(message);
        // and to the request
        curRequest.setAttribute("_errors",messages);
    }
    
    /**
     * helper to set info messages on the request
     */
    @SuppressWarnings("unchecked")
    public void addSuccessMessage(String message) {
        HttpServletRequest curRequest = 
                        ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                        .getRequest();
        FlashMap map = RequestContextUtils.getOutputFlashMap(curRequest);
        List<String> messages;
        // look for current errors
        if (map.containsKey("_messages")) {
            messages = (List<String>) map.get("_messages");
        } else {
            // create a new one
            messages = new ArrayList<String>();
            // attach it
            map.put("_messages",messages);
        }
        // add the message
        messages.add(message);
    }
    
    /**
     * helper to set warning messages on the request
     */
    @SuppressWarnings("unchecked")
    public void addWarningMessage(String message) {
        HttpServletRequest curRequest = 
                        ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                        .getRequest();
        FlashMap map = RequestContextUtils.getOutputFlashMap(curRequest);
        List<String> messages;
        // look for current errors
        if (map.containsKey("_warnings")) {
            messages = (List<String>) map.get("_warnings");
        } else {
            // create a new one
            messages = new ArrayList<String>();
            // attach it
            map.put("_warnings",messages);
        }
        // add the message
        messages.add(message);
    }
}