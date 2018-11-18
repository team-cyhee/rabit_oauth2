package com.cyhee.rabit.oauth.user.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyhee.rabit.exception.cmm.ValidationFailException;
import com.cyhee.rabit.oauth.email.service.EmailService;
import com.cyhee.rabit.oauth.user.model.User;
import com.cyhee.rabit.oauth.user.service.UserService;
import com.cyhee.rabit.oauth.user.validation.NotSnsGroup;

@RestController
@RequestMapping("v1/users")
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailService emailService;

	@PostMapping
	public ResponseEntity<Void> addUser(@RequestBody @Validated({NotSnsGroup.class}) User user, BindingResult bindingResult) {
		// TODO Handle Exception
		if (bindingResult.hasErrors())
			throw new ValidationFailException(bindingResult.getAllErrors());
	
		userService.addUser(user);
		if(user.getEmail() != null)
			emailService.registerEmail(user, user.getEmail());
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<Void> exists(@PathVariable String username) {
		if(userService.exists(username))
			return new ResponseEntity<>(HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
