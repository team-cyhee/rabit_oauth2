package com.cyhee.rabit.oauth.email.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyhee.rabit.oauth.email.service.EmailService;
import com.cyhee.rabit.oauth.user.service.UserService;

@RestController
@RequestMapping("v1/email")
public class EmailController {
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	UserService userService;

	@RequestMapping("/codes/{code}")
	public ResponseEntity<Void> verifyEmail(@PathVariable String code){
		emailService.verifyEmail(code);
		return ResponseEntity.accepted().build();
	}
	
}
