package com.devsuperior.dscatalog.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.devsuperior.dscatalog.components.JwtTokenEnhancer;

@Configuration
@EnableAuthorizationServer //diz que essa classe representa AuthorizationServer do OAuth 
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{//extendeu a classe do spring oauth
	
	//valores chaves do properties
	@Value("${security.oauth2.client.client-id}")
	private String clientId;
	
	@Value("${security.oauth2.client.client-secret}")
	private String clientSecret;
	
	@Value("${jwt.duration}")
	private Integer jwtDuration;
	
	//Beans injetadas para trabalharmos a authorizacao
	@Autowired
	private BCryptPasswordEncoder passwordEnconder;
	
	@Autowired
	private JwtAccessTokenConverter accessTokenConverter;
	
	@Autowired
	private JwtTokenStore tokenStore;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenEnhancer tokenEnhancer; //os dados adicionados no token
	
	//s??o m??todos da classe pai 
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// no objeto clients vamos definir quem vai ser nossa autenticacao e quais dados do cliente
		// credencias da aplicacao
		clients.inMemory()
		.withClient(clientId) //defini o cleintId, ou seja, qual o nome da aplicacao
		.secret(passwordEnconder.encode(clientSecret)) //qual senha da aplica????o (nao ?? a do usuario)
		.scopes("read", "write") //o acesso ?? de leitura e escrita
		.authorizedGrantTypes("password") //padr??o do oauth
		.accessTokenValiditySeconds(jwtDuration); //tempo de validade do token valido em milessegundo
	}
	
	// quem vai autorizar e qual o formato do token
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		
		TokenEnhancerChain chain = new TokenEnhancerChain();
		chain.setTokenEnhancers(Arrays.asList(accessTokenConverter, tokenEnhancer)); //vai aceitar uma lista
		
		
		endpoints.authenticationManager(authenticationManager)//?? ele que vai autenticar com a bean
		.tokenStore(tokenStore)//quais os objetos responsaveis por processsar o token
		.accessTokenConverter(accessTokenConverter)
		.tokenEnhancer(chain);//
	}
	
	
	
	
}
