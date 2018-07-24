package com.cyhee.rabit.oauth.user.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Long id;
	
    private String username;
 
    private String password;
    
    private String email;
}