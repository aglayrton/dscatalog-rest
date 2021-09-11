package com.devsuperior.dscatalog.service.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.devsuperior.dscatalog.DTO.UserInsertDTO;
import com.devsuperior.dscatalog.entities.User;
import com.devsuperior.dscatalog.repository.UserRepository;
import com.devsuperior.dscatalog.resources.exceptions.FieldMessage;

//essa classe diz ue UserInsertValid vai receber UserInsertDTO
//essa classe foi pegada do github do nelio, nao precisa estudar
public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserInsertDTO> {
	
	@Autowired
	private UserRepository repository;
	
	//nao vamos fazer nada, ela é usada quando é inicializado
	@Override
	public void initialize(UserInsertValid ann) {
	}
	
	
	//esse método ta testando se UserInsertDTO vai ser valido ou nao
	//retorna true ou false
	@Override
	public boolean isValid(UserInsertDTO dto, ConstraintValidatorContext context) {
		
		//criado list vazia
		List<FieldMessage> list = new ArrayList<>();
		
		// Coloque aqui seus testes de validação, acrescentando objetos FieldMessage à lista
		
		User user = repository.findByEmail(dto.getEmail());
		
		if(user!=null) {
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
