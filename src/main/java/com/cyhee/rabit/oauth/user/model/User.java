package com.cyhee.rabit.oauth.user.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="rabit_user")
@Getter @Setter
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Long id;
	
    private String username;
 
    private String password;
    
    private String email;
}