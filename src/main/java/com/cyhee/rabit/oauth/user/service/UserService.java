package com.cyhee.rabit.oauth.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cyhee.rabit.oauth.user.dao.UserRepository;
import com.cyhee.rabit.oauth.user.model.User;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
		
	public void addUser(User user) {    	
    	user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}
}
