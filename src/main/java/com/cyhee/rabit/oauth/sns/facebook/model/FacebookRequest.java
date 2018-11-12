package com.cyhee.rabit.oauth.sns.facebook.model;

import javax.servlet.http.HttpServletRequest;

import com.cyhee.rabit.oauth.sns.model.SnsProfile;
import com.cyhee.rabit.oauth.sns.model.SnsRequest;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FacebookRequest implements SnsRequest {
	
	private static final String endPoint = "https://graph.facebook.com/me?fields=name,email";
	private final HttpServletRequest request;
	private Gson gson = new GsonBuilder()
			.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
			.create(); 
	
	public FacebookRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public String getEndPoint() {
		return endPoint;
	}

	@Override
	public SnsProfile getProfile(String body) {
		return gson.fromJson(body, FacebookProfile.class);	
	}

	@Override
	public HttpServletRequest getHttpRequest() {
		return request;
	}

}
