package com.retro.food.core;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import com.retro.core.data.Entity;

/**
 * represents an user in the system
 * 
 * The spring specific stuff was copied from {@link org.springframework.security.core.userdetails.User}
 * 
 * @author <a href="mailto:mark@retrocognition.com">Mark Sullivan<a/>
 **/
public class User extends Entity implements UserDetails, CredentialsContainer {
    // serializable
    private static final long serialVersionUID = -7482239690658540974L;
    @NotNull @Min(value=1L)
    private long cafeId;
    @NotNull
    private String name;
    @NotNull
    private String userName;
    @NotNull @Pattern(regexp=".+@.+\\.[a-z]+")
    private String email;
    @NotNull
    private String status;
    
    // spring security fields
    private String password;
    private Set<GrantedAuthority> authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    
    /**
     * Construct the <code>User</code> with the details required by
     * {@link org.springframework.security.authentication.dao.DaoAuthenticationProvider}.
     *
     * @param username the username presented to the
     *        <code>DaoAuthenticationProvider</code>
     * @param password the password that should be presented to the
     *        <code>DaoAuthenticationProvider</code>
     * @param enabled set to <code>true</code> if the user is enabled
     * @param accountNonExpired set to <code>true</code> if the account has not
     *        expired
     * @param credentialsNonExpired set to <code>true</code> if the credentials
     *        have not expired
     * @param accountNonLocked set to <code>true</code> if the account is not
     *        locked
     * @param authorities the authorities that should be granted to the caller
     *        if they presented the correct username and password and the user
     *        is enabled. Not null.
     *
     * @throws IllegalArgumentException if a <code>null</code> value was passed
     *         either as a parameter or as an element in the
     *         <code>GrantedAuthority</code> collection
     */
    public User() {
        this.enabled = true;
        this.accountNonExpired = true;
        this.credentialsNonExpired = true;
        this.accountNonLocked = true;
    }
    
    private static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
        // Ensure array iteration order is predictable (as per UserDetails.getAuthorities() contract and SEC-717)
        SortedSet<GrantedAuthority> sortedAuthorities =
            new TreeSet<GrantedAuthority>(new AuthorityComparator());

        for (GrantedAuthority grantedAuthority : authorities) {
            Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
            sortedAuthorities.add(grantedAuthority);
        }

        return sortedAuthorities;
    }

    private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {
        // serializable
        private static final long serialVersionUID = -3784331359255080047L;

        public int compare(GrantedAuthority g1, GrantedAuthority g2) {
            // Neither should ever be null as each entry is checked before adding it to the set.
            // If the authority is null, it is a custom authority and should precede others.
            if (g2.getAuthority() == null) {
                return -1;
            }

            if (g1.getAuthority() == null) {
                return 1;
            }

            return g1.getAuthority().compareTo(g2.getAuthority());
        }
    }

    /**
     * Returns {@code true} if the supplied object is a {@code User} instance with the
     * same {@code username} value.
     * <p>
     * In other words, the objects are equal if they have the same username, representing the
     * same principal.
     */
    @Override
    public boolean equals(Object rhs) {
        if (rhs instanceof User) {
            return email.equals(((User) rhs).email);
        }
        return false;
    }

    /**
     * Returns the hashcode of the {@code username}.
     */
    @Override
    public int hashCode() {
        if(email != null ) {
            return email.hashCode();
        } else {
            return super.hashCode();
        }
    }
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }
    @Override
    public String getUsername() {
        return email;
    }
    @Override
    public boolean isEnabled() {
        return enabled;
    }
    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }
    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }
    @Override
    public void eraseCredentials() {
        password = null;
    }
    @Override
    public String getPassword() {
        return password;
    }
}