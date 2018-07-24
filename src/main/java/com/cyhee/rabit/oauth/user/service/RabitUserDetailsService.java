package com.cyhee.rabit.oauth.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cyhee.rabit.oauth.user.dao.UserRepository;
import com.cyhee.rabit.oauth.user.model.RabitUserPrincipal;
import com.cyhee.rabit.oauth.user.model.User;

@Service
public class RabitUserDetailsService implements UserDetailsService {
 
    @Autowired
    private UserRepository userRepository;
 
    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new RabitUserPrincipal(user);
    }
}