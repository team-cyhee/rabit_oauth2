package com.cyhee.rabit.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	//@Value("${config.oauth2.privateKey}")
	private String privateKey;

	//@Value("${config.oauth2.publicKey}")
	private String publicKey;
	
	private TestPasswordEncoder bCryptPasswordEncoder = new TestPasswordEncoder();
	
	private static class TestPasswordEncoder implements PasswordEncoder {

		@Override
		public String encode(CharSequence rawPassword) {
			return rawPassword.toString();
		}

		@Override
		public boolean matches(CharSequence rawPassword, String encodedPassword) {
			return rawPassword.toString().equals(encodedPassword);
		}
		
	}

	@Autowired
	private AuthenticationManager authenticationManager;

	@Bean
	public JwtAccessTokenConverter tokenEnhancer() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		//converter.setSigningKey(privateKey);
		//converter.setVerifierKey(publicKey);
		return converter;
	}

	@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(tokenEnhancer());
	}

	/**
	 * Defines the security constraints on the token endpoints /oauth/token_key and /oauth/check_token
	 * Client credentials are required to access the endpoints
	 *
	 * @param oauthServer
	 * @throws Exception
	 */
	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		PasswordEncoder en;
		oauthServer
		.passwordEncoder(bCryptPasswordEncoder)
		.tokenKeyAccess("isAnonymous() || hasRole('ROLE_TRUSTED_CLIENT')") // permitAll()
		.checkTokenAccess("hasRole('TRUSTED_CLIENT')") // isAuthenticated()
		;
	}

	/**
	 * Defines the authorization and token endpoints and the token services
	 *
	 * @param endpoints
	 * @throws Exception
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints

		// Which authenticationManager should be used for the password grant
		// If not provided, ResourceOwnerPasswordTokenGranter is not configured
		.authenticationManager(authenticationManager)

		// Use JwtTokenStore and our jwtAccessTokenConverter
		.tokenStore(tokenStore())
		.accessTokenConverter(tokenEnhancer())
		;
	}	

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
        // 클라이언트 아이디
    .withClient("my_client_id")
        // 클라이언트 시크릿
        .secret("my_client_secret")
        // 엑세스토큰 발급 가능한 인증 타입
        // 기본이 다섯개, 여기 속성이 없으면 인증 불가
        .authorizedGrantTypes("authorization_code", "password", "client_credentials", "implicit", "refresh_token")
        // 클라이언트에 부여된 권한
        .authorities("ROLE_MY_CLIENT")
        // 이 클라이언트로 접근할 수 있는 범위 제한
        // 해당 클라이언트로 API를 접근 했을때 접근 범위를 제한 시키는 속성
        .scopes("read", "write")
        // 이 클라이언트로 발급된 엑세스토큰의 시간 (단위:초)
        .accessTokenValiditySeconds(60 * 60 * 4)
        // 이 클라이언트로 발급된 리프러시토큰의 시간 (단위:초)
        .refreshTokenValiditySeconds(60 * 60 * 24 * 120)
    .and()
    .withClient("your_client_id")
        .secret("your_client_secret")
        .authorizedGrantTypes("authorization_code", "implicit")
        .authorities("ROLE_YOUR_CLIENT")
        .scopes("read")
    .and();
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");		
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");		
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");		
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");		
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");		
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");		
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");		
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");		
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");		
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");		
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
	}


}