package com.retro.food.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

//import com.google.appengine.api.utils.SystemProperty;

/**
 * indicates whether we are running in production or dev environments
 */
public class DevEnvironmentInterceptor implements HandlerInterceptor {

    final Logger _log = LoggerFactory.getLogger(DevEnvironmentInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler) throws Exception {
        return true;
    }

    /**
     * someday this may be useful
     */
    @Override
    public void postHandle(HttpServletRequest request,HttpServletResponse response,Object handler,ModelAndView modelAndView) throws Exception {
        if(modelAndView != null && (request.getServerName().equalsIgnoreCase("localhost") || request.getServerName().equalsIgnoreCase("local.suprwod.com"))) {
            modelAndView.addObject("_dev",true);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request,HttpServletResponse response,Object handler,Exception ex) throws Exception {
        //log.info("Page Time: [{}]", (System.currentTimeMillis() - startTime) * .001);
    }
}
