package com.devsuperior.dscatalog.service.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.devsuperior.dscatalog.DTO.UserUpdateDTO;
import com.devsuperior.dscatalog.entities.User;
import com.devsuperior.dscatalog.repository.UserRepository;
import com.devsuperior.dscatalog.resources.exceptions.FieldMessage;

//essa classe diz ue UserInsertValid vai receber UserInsertDTO
//essa classe foi pegada do github do nelio, nao precisa estudar
public class UserUpdateValidator implements ConstraintValidator<UserUpdateValid, UserUpdateDTO> {
	
	//acessar o codigo 2
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private UserRepository repository;
	
	//nao vamos fazer nada
	@Override
	public void initialize(UserUpdateValid ann) {
	}
	
	
	//esse método ta testando se UserInsertDTO vai ser valido ou nao
	//retorna true ou false
	@Override
	public boolean isValid(UserUpdateDTO dto, ConstraintValidatorContext context) {
		
		//pegando o id da url por exemplo
		//map<id, valor 2 por exemplo>, convertemos o resultado para um map de string
		@SuppressWarnings("unchecked")
		var uriVars = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE); //pega os atributos da url
		long userId = Long.parseLong(uriVars.get("id"));
		
		
		//criado list vazia
		List<FieldMessage> list = new ArrayList<>();
		
		// Coloque aqui seus testes de validação, acrescentando objetos FieldMessage à lista
		
		User user = repository.findByEmail(dto.getEmail());
		
		if(user != null && userId != user.getId()) {
			list.add(new FieldMessage("email", "Esse email já existe"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty(); //testa se a lista esta vazia, se estiver vazia, deu certo e nao deu erro
	}
}
