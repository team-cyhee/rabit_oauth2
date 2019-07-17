package com.cyhee.rabit.exception;

import java.util.List;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 해당 프로젝트에서 사용되는 http error에 대한 객체
 * error 발생 시 해당 객체를 반환할 것을 권장
 * @author chy
 *
 */
public class ApiError {
	private ApiErrorCode error;
	@JsonProperty
	private String message;
	
	public ApiError() {
		this.error = ApiErrorCode.UNKNOWN;
	}
	
	public ApiError(ApiErrorCode code, Throwable e) {
		this.error = code;
		this.message = e.getMessage();
	}

	public ApiError(ApiErrorCode code, String message) {
		this.error = code;
		this.message = message;
	}
	
	public ApiError(List<ObjectError> errors) {
		this.error = ApiErrorCode.INVALID_INPUT_TYPE;
		this.message = "";
		
		errors.forEach(error -> {
			if(error instanceof FieldError) {
				FieldError fieldError = (FieldError)error;
				this.message += fieldError.getField() + " : " + fieldError.getDefaultMessage() + "\n";
			}
		});
	}
	
	public ApiError(ApiException e) {
		this(e.getApiErrorCode(), e.getMessage());
	}

	public ApiErrorCode getError() {
		return error;
	}

	public void setError(ApiErrorCode error) {
		this.error = error;
	}
}
