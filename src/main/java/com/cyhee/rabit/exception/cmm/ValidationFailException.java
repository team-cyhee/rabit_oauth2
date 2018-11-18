package com.cyhee.rabit.exception.cmm;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.cyhee.rabit.exception.ApiErrorCode;
import com.cyhee.rabit.exception.ApiException;

import lombok.Getter;

/**
 * Validation test에 성공하지 못했을 때 발생하는 exception
 * 내부 변수로 validation errors를 가지고 있음
 * @author chy
 *
 */
@Getter
public class ValidationFailException extends ApiException {
	
	private static final long serialVersionUID = -6740715409616958417L;
	
	private List<ObjectError> errors;

	public ValidationFailException(List<ObjectError> errors) {
		super(getMessageFromErros(errors));
		this.errors = errors;
	}

	/**
	 * validation에서 발생한 errors를 하나의 String message로 만들어주는 함수
	 * @param errors - validation errors
	 * @return - one single String message from errors
	 */
	public static String getMessageFromErros(List<ObjectError> errors) {
		StringBuffer msg = new StringBuffer();
		errors.forEach(error -> {
			if(error instanceof FieldError) {
				FieldError fieldError = (FieldError)error;
				msg.append(fieldError.getField() + " : " + fieldError.getDefaultMessage() + ", ");
			}
		});
		// remove last ','
		return msg.delete(msg.length()-2, msg.length()-1).toString();
	}
	
	@Override
	public ApiErrorCode getApiErrorCode() {
		return ApiErrorCode.INVALID_INPUT_TYPE;
	}

	@Override
	public HttpStatus getStatus() {
		return HttpStatus.BAD_REQUEST;
	}
}
