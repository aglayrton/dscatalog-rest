package com.devsuperior.dscatalog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{

	@Autowired
	private JwtTokenStore tokenStore;
	
	//declaraoms os vetores
	private static final String[] PUBLIC = {"/oauth/token"};
	
	private static final String[] OPERATOR_OR_ADMIN = {"/produtos/**", "/categorias/**"};
	
	private static final String[] ADMIN = {"/users/**"};
	
	//vamos configurar o tokenStore
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(tokenStore);
	}
	
	
	//configurar as rotas
	@Override
	public void configure(HttpSecurity http) throws Exception {
		//quem pode acesser o que
		http.authorizeRequests()
		.antMatchers(PUBLIC).permitAll()
		.antMatchers(HttpMethod.GET, OPERATOR_OR_ADMIN).permitAll() //todos terão acesso aos endpoints que for GET nessasrotas
		.antMatchers(OPERATOR_OR_ADMIN).hasAnyRole("OPERATOR", "ADMIN") //Somente eles tem acsso
		.antMatchers(ADMIN).hasRole("ADMIN")
		.anyRequest().authenticated();//quem for acessar qualquer outra rota, tem que esta logado
	}

	
	
	
}
