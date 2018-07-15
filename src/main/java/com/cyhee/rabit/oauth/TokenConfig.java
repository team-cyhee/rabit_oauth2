package com.cyhee.rabit.oauth;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class TokenConfig {	
	@Autowired
	JwtAccessTokenConverter tokenConverter;

	private String privateKey;

	private String publicKey;

	@Autowired
	public TokenConfig(@Value("${config.oauth2.privateKey}") String privateKey, @Value("${config.oauth2.publicKey}") String publicKey) {
		this.privateKey = privateKey;
		this.publicKey = publicKey;
	}

	@Bean
	public JwtAccessTokenConverter tokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey(privateKey);
		converter.setVerifierKey(publicKey);
		return converter;
	}

	@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(tokenConverter);
	}

	@Bean
	@Primary
	public JdbcClientDetailsService jdbcClientDetailsService(DataSource dataSource) {
		return new JdbcClientDetailsService(dataSource);
	}
}
