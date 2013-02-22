package com.retro.food.security;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.retro.food.core.Cafe;
import com.retro.food.core.User;
import com.retro.food.data.dao.CafeDao;
import com.retro.food.data.dao.UserDao;

public class UserDetailsServiceImpl implements UserDetailsService {
    // logging
    final Logger _log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    // data
    private UserDao userDao;
    private CafeDao cafeDao;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        _log.debug("looking up user with username [{}]",username);
        User user = null;
        try {
            // look up the user
            user = getUserDao().getUserByEmail(username);
        } catch (Exception e) {
            // if there is an issue with the database driver, it tends to silently fail
            // making this exception catch necessary;
            _log.error("caught an exception on login - [{}]",e);
            throw new UsernameNotFoundException("no user found for username " + username);
        }
        // sanity check
        if(user == null) {
            _log.error("no user with email [{}]",username);
            throw new UsernameNotFoundException("no user found for username " + username);
        }
        _log.debug("got user [{}] , password [{}]",user.getEmail(),user.getPassword());
        // build the permissions array
        ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        // cool, looks for cafe ownerships
        List<Cafe> cafes = getCafeDao().findOwnedCafesByUserId(user.getId());
        _log.debug("cafes owned are [{}]",cafes);
        // check for results
        if(cafes != null && cafes.size() > 0) {
            // this person is an owner
            authorities.add(new SimpleGrantedAuthority("ROLE_OWNER"));
        } else {
            // not an owner
            authorities.add(new SimpleGrantedAuthority("ROLE_ATHLETE"));
        }
        // hardcode ryan, mark, bob as super users
        if(username.equalsIgnoreCase("mark@suprwod.com")) {
            authorities.add(new SimpleGrantedAuthority("ROLE_SUPER"));
            user.setSuper(true);
        }
        if(username.equalsIgnoreCase("bob@suprwod.com")) {
            authorities.add(new SimpleGrantedAuthority("ROLE_SUPER"));
            user.setSuper(true);
        }
        if(username.equalsIgnoreCase("ryan@suprwod.com")) {
            authorities.add(new SimpleGrantedAuthority("ROLE_SUPER"));
            user.setSuper(true);
        }
        if(username.equalsIgnoreCase("vanpoollen@gmail.com")) {
            authorities.add(new SimpleGrantedAuthority("ROLE_SUPER"));
            user.setSuper(true);
        }
        // set the authorities
        user.setAuthorities(authorities);
        // set the gyms owned
        user.setCafesOwned(cafes);
        // create the user details object
        return user;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public CafeDao getCafeDao() {
        return cafeDao;
    }

    public void setCafeDao(CafeDao cafeDao) {
        this.cafeDao = cafeDao;
    }
}
