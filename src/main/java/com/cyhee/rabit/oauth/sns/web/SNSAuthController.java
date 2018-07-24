package com.cyhee.rabit.oauth.sns.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyhee.rabit.oauth.sns.exception.NotSignUpException;
import com.cyhee.rabit.oauth.sns.service.SNSTokenService;

@RestController
public class SNSAuthController {
	@Autowired
	SNSTokenService createTokenService;

	@RequestMapping("/oauth/token/naver")
	ResponseEntity<OAuth2AccessToken> authByNaver(HttpServletRequest request) {
		return new ResponseEntity<>(createTokenService.authByNaver(request), HttpStatus.ACCEPTED);
	}
	
	@ExceptionHandler(value = InvalidTokenException.class)
	public ResponseEntity<Exception> invalidTokenHandler(InvalidTokenException e) {
		return new ResponseEntity<>(e, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(value = NotSignUpException.class)
	public ResponseEntity<NotSignUpException> notSingUpHandler(NotSignUpException e) {
		return new ResponseEntity<>(e, HttpStatus.PRECONDITION_REQUIRED);
	}
}
