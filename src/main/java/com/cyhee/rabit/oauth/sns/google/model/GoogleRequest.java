package com.cyhee.rabit.oauth.sns.google.model;

import javax.servlet.http.HttpServletRequest;

import com.cyhee.rabit.oauth.sns.model.SnsProfile;
import com.cyhee.rabit.oauth.sns.model.SnsRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GoogleRequest implements SnsRequest {
	
	private static final String endPoint = "https://www.googleapis.com/plus/v1/people/me";
	private final HttpServletRequest request;
	private Gson gson = new GsonBuilder()
			.create();
	
	public GoogleRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public String getEndPoint() {
		return endPoint;
	}

	@Override
	public SnsProfile getProfile(String body) {
		return gson.fromJson(body, GoogleProfile.class);	
	}

	@Override
	public HttpServletRequest getHttpRequest() {
		return request;
	}

}