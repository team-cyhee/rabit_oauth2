package com.cyhee.rabit.oauth.sns.naver.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NaverResponse {

	private String resultcode;
	
	private String message;
	
	private NaverProfile response;
}
