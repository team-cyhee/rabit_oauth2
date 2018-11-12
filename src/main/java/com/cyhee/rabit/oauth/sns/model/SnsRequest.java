package com.cyhee.rabit.oauth.sns.model;

import javax.servlet.http.HttpServletRequest;

public interface SnsRequest {	
	String getEndPoint();
	SnsProfile getProfile(String body);
	HttpServletRequest getHttpRequest();
}
