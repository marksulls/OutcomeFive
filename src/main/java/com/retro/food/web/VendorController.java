package com.retro.food.web;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.retro.core.util.PasswordUtil;
import com.retro.food.core.Cafe;
import com.retro.food.core.CafeUser;
import com.retro.food.core.User;
import com.retro.food.web.model.Registration;

/**
 * operations related to user registration
 * @author mark
 */
@Controller
public class VendorController extends BaseController {
    // logging
    final Logger _log = LoggerFactory.getLogger(VendorController.class);
    
    /**
     * render or change the users password
     */
    @RequestMapping(value="/register",method=RequestMethod.GET)
    public String create(Model model) {
        // create a new registration
        Registration registration = new Registration();
        model.addAttribute("registration",registration);
        // load up the form
        return "register/create";
    }
    
    /**
     * saves a new registration
     */
    @RequestMapping(value="/register",method=RequestMethod.POST)
    public String save(@Valid Registration registration,BindingResult result,Model model) {
        // check for errors
        if(result.hasErrors()) {
            _log.warn("validation failed with errors - [{}]",result.getAllErrors());
            addErrorMessage("Not all required fields were filled in");
            return "register/create";
        }
        // verify this email doesn't already exist
        User existing = getUserDao().getUserByEmail(registration.getEmail());
        // sanity check
        if(existing != null) {
            _log.warn("user with email [{}] already exists.",registration.getEmail());
            // add an error
            result.addError(new ObjectError("email","The email is already in use."));
            addErrorMessage("The email " + registration.getEmail() + " is already in use");
            return "register/create";
        }
        // encrypt the password
        String password = PasswordUtil.encryptPassword(registration.getPassword());
        // do this in a transaction
        PlatformTransactionManager transactionManager = getUserDao().getTransactionManager();
        // get the connection
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
         // explicitly setting the transaction name 
        def.setName("RegistrationTX");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        // start the transaction
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            // create a new user
            User user = new User();
            user.setEmail(registration.getEmail());
            user.setName(registration.getName());
            user.setPassword(password);
            user.setActive(true);
            // save it
            getUserDao().saveOrUpdate(user);
            // create the cafe
            Cafe cafe = new Cafe();
            cafe.setName(registration.getCafeName());
            cafe.setTimeZone("America/Denver");
            // save it
            getCafeDao().saveOrUpdate(cafe);
            // now add the cafe user mapping
            CafeUser cafeUser = new CafeUser();
            cafeUser.setCafeId(cafe.getId());
            cafeUser.setUserId(user.getId());
            cafeUser.setType(3);
            // save it
            getCafeUserDao().saveOrUpdate(cafeUser);
            // commit the transaction
            transactionManager.commit(status);
            // add the basic perms
            Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority("ROLE_OWNER"));
            user.setAuthorities(authorities);
            // log them in
            Authentication authentication = new UsernamePasswordAuthenticationToken(user,user.getPassword(),user.getAuthorities());
            // set the Authentication in the SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            _log.error("error registering [{}] - [{}]",registration,e);
            transactionManager.rollback(status);
            return "register/create";
        }
        addSuccessMessage("Welcome " + registration.getName() + ", Thanks for registering!");
        return "redirect:/";
    }
}