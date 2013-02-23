package com.retro.food.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;

import com.retro.core.util.PasswordUtil;
import com.retro.food.core.User;
import com.retro.food.web.model.PasswordChange;

/**
 * operations related to user profile management
 * @author mark
 */
@Controller
@RequestMapping("/profile")
public class ProfileController extends BaseController {
    // logging
    final Logger _log = LoggerFactory.getLogger(ProfileController.class);
    
    /**
     * render or change the users password
     */
    @RequestMapping(value="/password")
    public String password(@Valid PasswordChange pModel,BindingResult result,HttpServletRequest request,Model model) throws Exception {
        _log.info("entering password, request method is [{}]",request.getMethod());
        // get the current user
        User user = getCurrentUser();
        // check the method
        if(request.getMethod().equalsIgnoreCase("POST")) {
            // verify the binding
            if(!result.hasErrors()) {
                // match the confirm / password
                if(pModel.getPassword().equals(pModel.getConfirm()) == false) {
                    // add an error
                    result.addError(new ObjectError("confirm","The passwords do not match"));
                } else {
                    // cool, encrypt the password
                    String password = PasswordUtil.encryptPassword(pModel.getPassword());
                    _log.debug("the password is [{}]",password);
                    user.setPassword(password);
                    // save the user
                    getUserDao().saveOrUpdate(user);
                    // add a success message
                    addSuccessMessage("Password Successfully Updated");
                    return "redirect:/";
                }
            }
            // else add an error and let fall through
            addErrorMessage("Could not change password. Not all required fields were filled in or passwords did not match.");
            model.addAttribute("pModel",pModel);
        } else {
            // clear these out
            pModel = new PasswordChange();
            pModel.setUserId(user.getId());
            // create the password change form
            model.addAttribute("pModel",pModel);
            result = null;
        }
        // load up the form
        return "profile/password";
    }
}