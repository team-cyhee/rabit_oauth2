package com.cyhee.rabit.oauth.email.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cyhee.rabit.exception.cmm.NoSuchContentException;
import com.cyhee.rabit.oauth.email.service.EmailService;
import com.cyhee.rabit.oauth.user.model.User;
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
	
	@PostMapping("/find-pass")
	public ResponseEntity<Void> findPassword(@RequestParam String username) {
		User user = userService.getByUsername(username);
		if(user.getEmail() == null)
			throw new NoSuchContentException("There is no email registerated for user " + username);
		emailService.findPassword(user);
		return ResponseEntity.accepted().build();
	}
}
