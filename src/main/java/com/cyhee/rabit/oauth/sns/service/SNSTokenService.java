package com.cyhee.rabit.oauth.sns.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

import com.cyhee.rabit.oauth.sns.exception.NotSignUpException;
import com.cyhee.rabit.oauth.sns.naver.model.NaverProfile;
import com.cyhee.rabit.oauth.sns.naver.service.NaverTokenService;
import com.cyhee.rabit.oauth.user.dao.UserRepository;
import com.cyhee.rabit.oauth.user.model.User;

@Service("createTokenService")
public class SNSTokenService {	

	@Autowired
	private DefaultTokenServices tokenServices;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private NaverTokenService naverService;

	public OAuth2AccessToken authByNaver(HttpServletRequest request) {
		NaverProfile response = naverService.getResponse(request);		
		String email = response.getEmail();
		User user = userRepository.findByEmail(email);
		if(user == null)
			throw new NotSignUpException(email + " not yet signed up.");

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
