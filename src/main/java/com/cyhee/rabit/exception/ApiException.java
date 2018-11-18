package com.cyhee.rabit.exception;

import org.springframework.http.HttpStatus;

/**
 * Rest Api에서 발생하는 Exception에 대한 interface
 * 해당 interface를 구현한 Exception을 이용할 것을 권장
 * @author chy
 *
 */
public abstract class ApiException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ApiException() {
		super();
	}
	
	public ApiException(String msg) {
		super(msg);
	}
	
	public abstract ApiErrorCode getApiErrorCode();	
	public abstract HttpStatus getStatus();
}
