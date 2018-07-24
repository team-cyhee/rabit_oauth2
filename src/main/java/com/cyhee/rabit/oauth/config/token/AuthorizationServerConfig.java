package com.cyhee.rabit.oauth.config.token;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	@Autowired 
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JdbcClientDetailsService clientDetailsService;

	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private JwtAccessTokenConverter tokenConverter;
	
	@Bean
	public TokenEnhancer tokenEnhancer() {
		return new UserTokenEnhancer();
	}	
	
	@Bean
	public TokenEnhancerChain tokenEnhancerChain() {
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		List<TokenEnhancer> list = new ArrayList<>();
		list.add(tokenEnhancer());
		list.add(tokenConverter);
		tokenEnhancerChain.setTokenEnhancers(list);
		return tokenEnhancerChain;
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		PasswordEncoder encoder = new BCryptPasswordEncoder(12);
		oauthServer
			.passwordEncoder(encoder)
			.tokenKeyAccess("permitAll()") // permitAll()
			.checkTokenAccess("isAuthenticated()") // isAuthenticated()
		;
	}
	
	@Bean
	@Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore);
        defaultTokenServices.setSupportRefreshToken(true);
        defaultTokenServices.setTokenEnhancer(tokenEnhancerChain());
        defaultTokenServices.setAuthenticationManager(authenticationManager);
        defaultTokenServices.setClientDetailsService(clientDetailsService);
        return defaultTokenServices;
    }


	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		super.configure(endpoints);
		endpoints
			.tokenStore(tokenStore)
			.tokenEnhancer(tokenEnhancerChain())
			//.accessTokenConverter(tokenConverter)
			.authenticationManager(authenticationManager);
		;
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.withClientDetails(clientDetailsService);
	}

}