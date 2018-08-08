package com.cyhee.rabit.oauth.sns.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cyhee.rabit.oauth.sns.naver.model.NaverProfile;
import com.cyhee.rabit.oauth.sns.naver.service.NaverTokenService;

@RestController
public class SNSProfileController {
	
	@Autowired
	private NaverTokenService naverTokenService;
	
	@RequestMapping(value="/oauth/naver/profile",method=RequestMethod.GET)
	public ResponseEntity<NaverProfile> getNaverProfile(HttpServletRequest request) {
		NaverProfile response = naverTokenService.getResponse(request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
