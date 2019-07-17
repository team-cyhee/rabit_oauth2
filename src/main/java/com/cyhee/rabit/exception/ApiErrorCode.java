package com.cyhee.rabit.exception;

/**
 * HttpStatus로 나타내기 부족한 정보들을 더 자세히 나타내기 위한 enum
 * @author chy
 *
 */
public enum ApiErrorCode {
	DUPLICATED_KEY,
	INVALID_INPUT_TYPE,
	UNKNOWN,
	NOT_FOUND,
	FORBIDDEN,
	TOKEN_AUTHORIZATION_FAIL,
	UNAUTHORIZED,
	UNSUPPROTED_CONTENT_TYPE,
}
