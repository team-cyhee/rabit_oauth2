package com.cyhee.rabit.oauth.sns.exception;

public class NotSignUpException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public NotSignUpException(String msg, Throwable t) {
		super(msg, t);
	}

	public NotSignUpException(String msg) {
		super(msg);
	}
}
