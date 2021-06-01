package com.flightapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.flightapp.model.ExecutiveUser;
import com.flightapp.repository.ExecutiveUserRepository;


@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    private ExecutiveUserRepository userRepository;

    @Autowired
    private LoginAttemptService loginAttemptService;
 
    @Autowired
    private HttpServletRequest request;


    @Override
    @Transactional(readOnly = true)
    public ACUserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        
        String ip = getClientIP();
        if (loginAttemptService.isBlocked(ip)) {
            throw new RuntimeException("blocked");
        }
        
        ExecutiveUser user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
        return new ACUserDetails(user);
    }

    private String getClientIP() {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null){
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}