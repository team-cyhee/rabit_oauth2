package com.cyhee.rabit.oauth.sns.naver.model;

import javax.servlet.http.HttpServletRequest;

import com.cyhee.rabit.oauth.sns.model.SnsProfile;
import com.cyhee.rabit.oauth.sns.model.SnsRequest;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class NaverRequest implements SnsRequest {
	
	private static final String endPoint = "https://openapi.naver.com/v1/nid/me";
	private final HttpServletRequest request;
	private Gson gson = new GsonBuilder()
			.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
			.create();
	
	public NaverRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public String getEndPoint() {
		return endPoint;
	}

	@Override
	public SnsProfile getProfile(String body) {
		NaverResponse response = gson.fromJson(body, NaverResponse.class);
		if(!response.getResultcode().equals("00"))
			throw new NaverResponseFailException(response);
		return response.getResponse();
	}

	@Override
	public HttpServletRequest getHttpRequest() {
		return request;
	}

}