package com.devsuperior.dscatalog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private BCryptPasswordEncoder passwordeEnconder;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// qual o algoritmo que esta sendo usado para criptografar
		//e quem é o userdetailsservice
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordeEnconder);
		//agora o spring sabe como chamar o usuario por email e como analisar
		// a senha criptografada
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		//actuator é do spring cloud oauth usa
		web.ignoring().antMatchers("/actuator/**");
	}

	@Override
	@Bean // colocamos aqui para se tornar um bean
	protected AuthenticationManager authenticationManager() throws Exception {
		// efetua a autenticacao
		return super.authenticationManager();
	}
	
	
}
