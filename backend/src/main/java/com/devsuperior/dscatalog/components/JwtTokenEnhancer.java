package com.devsuperior.dscatalog.components;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.devsuperior.dscatalog.entities.User;
import com.devsuperior.dscatalog.repository.UserRepository;

@Component
public class JwtTokenEnhancer implements TokenEnhancer {//interface do oauth2
	
	@Autowired
	private UserRepository repository;
	
	
	//serve pra na hora que for gerar o token ele implementa os objetos que vc passar
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		User user = repository.findByEmail(authentication.getName());
		
		//acrescentado objetos no token
		//Map<chave, valor>
		Map<String, Object> map = new HashMap<>();
		
		map.put("userFirstName", user.getFirstName());
		map.put("userId", user.getId());
		
		//o token vai ficar com as informações adicionais
		//a classe abaixo é uma subclasse de OAuth2AccessToken
		//so esse tipo que tem a opção de adicionar no nosso token
		DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) accessToken;
		token.setAdditionalInformation(map);
		
		return token; //ou accessToken
	}

}
