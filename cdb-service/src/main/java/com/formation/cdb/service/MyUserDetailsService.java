package com.formation.cdb.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formation.cdb.entity.impl.UserRole;
import com.formation.cdb.persistence.UserDao;



// TODO: Auto-generated Javadoc
/**
 * The Class MyUserDetailsService.
 */
@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    /** The user dao. */
    @Autowired
    private UserDao userDao;

    /* (non-Javadoc)
     * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
     */
    @Transactional(readOnly=true)
    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
    
        com.formation.cdb.entity.impl.User user = userDao.findByUserName(username);
        List<GrantedAuthority> authorities = buildUserAuthority(user.getUserRole());

        return buildUserForAuthentication(user, authorities);
        
    }

    // Converts com.mkyong.users.model.User user to
    /**
     * Builds the user for authentication.
     *
     * @param user the user
     * @param authorities the authorities
     * @return the user
     */
    // org.springframework.security.core.userdetails.User
    private User buildUserForAuthentication(com.formation.cdb.entity.impl.User user, List<GrantedAuthority> authorities) {
        return new User(user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true, authorities);
    }

    /**
     * Builds the user authority.
     *
     * @param userRoles the user roles
     * @return the list
     */
    private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {

        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

        // Build user's authorities
        for (UserRole userRole : userRoles) {
            setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
        }

        List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

        return Result;
    }

}