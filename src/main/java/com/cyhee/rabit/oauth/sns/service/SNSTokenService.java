package com.cyhee.rabit.oauth.sns.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.cyhee.rabit.oauth.sns.exception.NotSignUpException;
import com.cyhee.rabit.oauth.sns.facebook.model.FacebookProfile;
import com.cyhee.rabit.oauth.sns.model.SnsProfile;
import com.cyhee.rabit.oauth.sns.model.SnsRequest;
import com.cyhee.rabit.oauth.sns.naver.model.NaverProfile;
import com.cyhee.rabit.oauth.sns.naver.model.NaverResponse;
import com.cyhee.rabit.oauth.sns.naver.model.NaverResponseFailException;
import com.cyhee.rabit.oauth.user.dao.UserRepository;
import com.cyhee.rabit.oauth.user.model.User;
import com.cyhee.rabit.oauth.user.service.UserService;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class SnsTokenService {	

	@Autowired
	private DefaultTokenServices tokenServices;
	@Autowired
	private UserRepository userRepository;	
	@Autowired
	private TokenExtractor tokenExtractor;
	@Autowired
	private UserService userService;
	
	private static final String naverUrl = "https://openapi.naver.com/v1/nid/me"; 
	private static final String facebookUrl = "https://graph.facebook.com/me?fields=name,email";

	private Gson gson = new GsonBuilder()
			.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
			.create();

	public OAuth2AccessToken authByNaver(HttpServletRequest request) {
		String token = extractToken(request);
		ResponseEntity<String> tokenResponse = getResponseFromAuthorizationServer(naverUrl, token);
		
		NaverResponse response = gson.fromJson(tokenResponse.getBody(), NaverResponse.class);
		if(!response.getResultcode().equals("00"))
			throw new NaverResponseFailException(response);
		NaverProfile profile = response.getResponse();		
		
		User user = getUser(profile);		
		return createRabitAccessToken(user);
	}
	
	public void signupByFacebook(HttpServletRequest request, User userForm) {
		SnsProfile profile = getFacebookProfile(request);
		userForm.setEmail(profile.getEmail());
		userService.addUser(userForm);
	}
	
	public OAuth2AccessToken authByFacebook(HttpServletRequest request) {
		SnsProfile profile = getFacebookProfile(request);
		User user = getUser(profile);
		return createRabitAccessToken(user);
	}
	
	public void signUp(SnsRequest request, User userForm) {
		SnsProfile profile = getProfile(request);
		userForm.setEmail(profile.getEmail());
		userService.addUser(userForm);		
	}
	
	public OAuth2AccessToken signIn(SnsRequest request) {
		SnsProfile profile = getProfile(request);
		User user = getUser(profile);
		return createRabitAccessToken(user);
	}
	
	private SnsProfile getFacebookProfile(HttpServletRequest request) {
		String token = extractToken(request);
		ResponseEntity<String> tokenResponse = getResponseFromAuthorizationServer(facebookUrl, token);
		
		return gson.fromJson(tokenResponse.getBody(), FacebookProfile.class);		
	}
	
	private SnsProfile getProfile(SnsRequest request) {
		String token = extractToken(request.getHttpRequest());
		ResponseEntity<String> tokenResponse = getResponseFromAuthorizationServer(request.getEndPoint(), token);
		return request.getProfile(tokenResponse.getBody());
	}
	
	private User getUser(SnsProfile profile) {
		String email = profile.getEmail();
		User user = userRepository.findByEmail(email);
		if(user == null)
			throw new NotSignUpException(email + " not yet signed up.");
		return user;
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
			throw new InvalidTokenException("Unauthorized token");
		}
	}
	
	private OAuth2AccessToken createRabitAccessToken(User user) {
		Authentication userAuhentication = new PreAuthenticatedAuthenticationToken(user.getUsername(), "naver");

		// TODO set correct OAuth2Request
		Map<String, String> requestParameters = null;
		String clientId = "rabit_rest";
		Collection<? extends GrantedAuthority> authorities = null;
		boolean approved = true;
		Set<String> scope = null;
		Set<String> resourceIds = null;
		String redirectUri = null;
		Set<String> responseTypes = null;
		Map<String, Serializable> extensionProperties = null;

		OAuth2Request storedRequest = new OAuth2Request(requestParameters, clientId, authorities,
				approved, scope, resourceIds, redirectUri, responseTypes, extensionProperties);
		OAuth2Authentication auth = new OAuth2Authentication(storedRequest, userAuhentication);
		return tokenServices.createAccessToken(auth);
	}
}
