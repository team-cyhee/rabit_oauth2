package com.cyhee.rabit.oauth.sns.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyhee.rabit.oauth.sns.facebook.model.FacebookRequest;
import com.cyhee.rabit.oauth.sns.google.model.GoogleRequest;
import com.cyhee.rabit.oauth.sns.model.SnsRequest;
import com.cyhee.rabit.oauth.sns.naver.model.NaverRequest;
import com.cyhee.rabit.oauth.sns.service.SnsTokenService;
import com.cyhee.rabit.oauth.user.model.User;
import com.cyhee.rabit.oauth.user.validation.SetPasswordGroup;

import javassist.NotFoundException;

@RestController
@RequestMapping("/oauth/token/")
public class SNSAuthController {
	@Autowired
	SnsTokenService snsTokenService;
	
	@GetMapping("{social}")
	ResponseEntity<OAuth2AccessToken> signIn(HttpServletRequest request, @PathVariable String social) throws NotFoundException {
		SnsRequest snsRequest = getSnsRequest(social, request);
		if(snsRequest == null) throw new NotFoundException("Not supported sns");
		return new ResponseEntity<>(snsTokenService.signIn(snsRequest), HttpStatus.ACCEPTED);
	}
	
	@PostMapping("{social}")
	public ResponseEntity<Void> signUp(HttpServletRequest request, @PathVariable String social,
			@RequestBody @Validated({SetPasswordGroup.class}) User user, BindingResult bindingResult) throws NotFoundException {
		
		SnsRequest snsRequest = getSnsRequest(social, request);
		if(snsRequest == null) throw new NotFoundException("Not supported sns");
		
		// TODO Handle Exception
		if (bindingResult.hasErrors())
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	
		snsTokenService.signUp(snsRequest, user);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	private SnsRequest getSnsRequest(String social, HttpServletRequest request) {
		switch(social) {
			case "facebook":
				return new FacebookRequest(request);
			case "google": 
				return new GoogleRequest(request);
			case "naver":
				return new NaverRequest(request);
			default:
				return null;
		}		
	}
}
