package com.cyhee.rabit.exception.cmm;

import org.springframework.http.HttpStatus;

import com.cyhee.rabit.exception.ApiErrorCode;
import com.cyhee.rabit.exception.ApiException;

public class UnsupportedContentException extends ApiException {

	private static final long serialVersionUID = 2701283839580016197L;
	
	public UnsupportedContentException() {
		super("Unsupported content");
	}
	
	public UnsupportedContentException(String msg) {
		super(msg);
	}

	@Override
	public ApiErrorCode getApiErrorCode() {
		return ApiErrorCode.UNSUPPROTED_CONTENT_TYPE;
	}

	@Override
	public HttpStatus getStatus() {
		return HttpStatus.BAD_REQUEST;
	}

}
