package com.cyhee.rabit.oauth.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cyhee.rabit.oauth.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	 
    User findByUsername(String username);
    
    User findByEmail(String email);
}