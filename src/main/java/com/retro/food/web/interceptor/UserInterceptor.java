package com.retro.food.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.retro.food.core.User;
import com.retro.food.data.dao.CafeDao;
import com.retro.food.data.dao.DaoFactory;
import com.retro.food.data.dao.UserDao;

/**
 * sets the _user and _cafe request variables for ease of use
 */
public class UserInterceptor implements HandlerInterceptor {
    // logging
    final static Logger _log = LoggerFactory.getLogger(UserInterceptor.class);
    private CafeDao cafeDao;
    private UserDao userDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        _log.info("entering preHandle for request [{}]", request.getRequestURI());
        // exclude the set gym page
        if (request.getRequestURI().startsWith("/cafe/switch") || request.getRequestURI().startsWith("/cafe/set")) {
            // setting gym, continue
            return true;
        }
        // check if spring has been configured yet
        HttpSession session = request.getSession();
        // no one authenticated yet
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            _log.info("no auth");
            // spring security not configured, continue request
            return true;
        }
        // make sure there is a current gym set
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = getUserFromPrincipal(principal);
        // this will be null if the user is anonymous
        if(user == null) {
            _log.info("no principal");
            return true;
        }
//        // check if there is a current gym set
//        if (session.getAttribute("currentGymId") == null) {
//            // if the user has a default gym
//            if(athlete.getGymId() != 0) {
//                _log.debug("the athlete [{}] has only one gym",athlete);
//                session.setAttribute("currentGymId",athlete.getGymId());
//                return true;
//            } else if(athlete.getGymsOwned() != null && athlete.getGymsOwned().size() == 1) {
//                _log.debug("the athlete [{}] has only one gym",athlete);
//                session.setAttribute("currentGymId",athlete.getGymsOwned().get(0).getId());
//                return true;
//            } 
//            // else gym owner, send to selection
//            response.sendRedirect("/gym/switch");
//            return false;
//        } 
        // else nothing to do
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView == null) {
            // nothing to set global view variables to
            return;
        }
        // sanity check for none authenticated
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            // spring security not configured, continue request
            return;
        }
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            modelAndView.addObject("_user", getUserFromPrincipal(principal));
        }
        if (request.getSession().getAttribute("currentGymId") != null) {
            Long cafeId = (Long) request.getSession().getAttribute("currentCafeId");
            _log.debug("setting current cafe to [{}]",cafeId);
            modelAndView.addObject("_cafe", getCafeDao().getObjectById(cafeId));
        }
        if(request.getMethod().equalsIgnoreCase("GET") 
                        && request.getRequestURI().endsWith("switch") == false
                        && request.getRequestURI().endsWith("favicon.ico") == false) {
            // store the last page that was requested
            request.getSession().setAttribute("lastHandledPage", request.getRequestURI());
        }
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request,HttpServletResponse response,Object handler,Exception ex) throws Exception {
        // TODO Auto-generated method stub
    }
    
    /**
     * Retrieves the current athlete from the principal object
     * @param principal
     * @return
     */
    public static User getUserFromPrincipal(Object principal) {
        _log.info("principal is [{}]",principal);
        // check for anonymous
        if(principal.equals("anonymousUser")) {
            // anonoymous user
            return null;
        }
        _log.debug("auth is is [{}]",SecurityContextHolder.getContext().getAuthentication());
        User user = null;
        // social generally returns a string
        if(principal instanceof String) {
            user = DaoFactory.getInstance().getUserDao().getObjectById(Long.valueOf((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
        } else {
            // else get the security principal (should be an athlete)
            // TODO: sanity check this
            user = (User)principal;
        }
        _log.info("context holder is [{}]",user);
        return user;
    }

    public CafeDao getCafeDao() {
        return cafeDao;
    }

    public void setCafeDao(CafeDao cafeDao) {
        this.cafeDao = cafeDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
