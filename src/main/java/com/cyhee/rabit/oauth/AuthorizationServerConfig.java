package com.cyhee.rabit.oauth;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	private TestPasswordEncoder bCryptPasswordEncoder = new TestPasswordEncoder();

	@Autowired
	private JdbcClientDetailsService clientDetailsService;

	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private JwtAccessTokenConverter tokenConverter;

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
		.tokenKeyAccess("permitAll()") // permitAll()
		.checkTokenAccess("isAuthenticated()") // isAuthenticated()
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
		super.configure(endpoints);
		endpoints
		//
		//		// Which authenticationManager should be used for the password grant
		//		// If not provided, ResourceOwnerPasswordTokenGranter is not configured
		//		.authenticationManager(authenticationManager)
		//
		//		// Use JwtTokenStore and our jwtAccessTokenConverter
		.tokenStore(tokenStore)
		.accessTokenConverter(tokenConverter)
		;
	}	

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.withClientDetails(clientDetailsService);
	}


}