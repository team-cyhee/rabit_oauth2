package com.cyhee.rabit.oauth.sns.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cyhee.rabit.oauth.sns.exception.NotSignUpException;
import com.cyhee.rabit.oauth.sns.naver.model.NaverResponseFailException;

@ControllerAdvice
@RestController
public class SNSExceptionHandler {

	@ExceptionHandler(value=NaverResponseFailException.class)
	public ResponseEntity<NaverResponseFailException> naverResponseFailHandler(NaverResponseFailException e) {
		return new ResponseEntity<>(e, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(value=NotSignUpException.class)
	public ResponseEntity<NotSignUpException> notSignUpHandler(NotSignUpException e) {
		return new ResponseEntity<>(e, HttpStatus.PRECONDITION_REQUIRED);
	}
	
	@ExceptionHandler(value=InvalidTokenException.class)
	public ResponseEntity<Exception> invalidTokenHandler(InvalidTokenException e) {
		return new ResponseEntity<>(e, HttpStatus.UNAUTHORIZED);
	}
}
