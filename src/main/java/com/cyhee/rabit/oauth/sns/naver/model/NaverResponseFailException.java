package com.cyhee.rabit.oauth.sns.naver.model;

public class NaverResponseFailException extends RuntimeException {

	private static final long serialVersionUID = -3342061559949592433L;

	public NaverResponseFailException(NaverResponse response) {
		super(String.format("[%s] %s",response.getResultcode(),response.getMessage()));
	}
}
