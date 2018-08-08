package com.cyhee.rabit.oauth.sns.naver.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.cyhee.rabit.oauth.sns.naver.model.NaverProfile;
import com.cyhee.rabit.oauth.sns.naver.model.NaverResponse;
import com.cyhee.rabit.oauth.sns.naver.model.NaverResponseFailException;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class NaverTokenService {
	
	private TokenExtractor tokenExtractor = new BearerTokenExtractor();
	
	public NaverProfile getResponse(HttpServletRequest request) {
		String token = extractToken(request);
		ResponseEntity<String> tokenResponse = getResponseFromAuthorizationServer("https://openapi.naver.com/v1/nid/me", token);
		String responseBody = tokenResponse.getBody();
				
		Gson gson = new GsonBuilder()
				.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
				.create();

		NaverResponse response = gson.fromJson(responseBody, NaverResponse.class);
		if(!response.getResultcode().equals("00"))
			throw new NaverResponseFailException(response);
		
		return response.getResponse();
	}
	
	private String extractToken(HttpServletRequest request) {		
		Authentication authentication = tokenExtractor.extract(request);
		if(authentication == null)
			throw new InvalidTokenException("No bearer token found");
		
		return (String)authentication.getPrincipal();		
	}
	
	private ResponseEntity<String> getResponseFromAuthorizationServer(String url, String token) {		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders header = new HttpHeaders();
		header.add("Authorization", "Bearer " + token);
		HttpEntity<Object> entity = new HttpEntity<>(header);
		
		try {
			ResponseEntity<String> tokenResponse = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
			return tokenResponse;
		} catch (RestClientException e) {
			throw new InvalidTokenException("Unauthorized naver token");
		}
	}
}
