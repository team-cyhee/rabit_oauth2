package com.cyhee.rabit.exception.cmm;

import org.springframework.http.HttpStatus;

import com.cyhee.rabit.exception.ApiErrorCode;
import com.cyhee.rabit.exception.ApiException;

/**
 * 접근을 시도한 객체가 존재하지 않을 때 발생시키는 Exception
 * @author chy
 *
 */
public class NoSuchContentException extends ApiException {
	private static final long serialVersionUID = 1L;
	
	public NoSuchContentException(String msg) {
		super(msg);
	}
	public ApiErrorCode getApiErrorCode() {
		return ApiErrorCode.NOT_FOUND;
	}

	@Override
	public HttpStatus getStatus() {
		return HttpStatus.NOT_FOUND;
	}
}
