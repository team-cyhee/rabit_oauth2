package com.cyhee.rabit.oauth.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cyhee.rabit.exception.cmm.NoSuchContentException;
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
	
	public User getByUsername(String username) {
		User user = userRepository.findByUsername(username);
		if(user == null)
			throw new NoSuchContentException("No such user with username " + username);
		return user;
	}
	
	public void changePassword(User user, String password) {	
    	user.setPassword(passwordEncoder.encode(password));
		userRepository.save(user);
	}
	
	public boolean exists(String username) {
		return userRepository.existsByUsername(username);
	}
}
