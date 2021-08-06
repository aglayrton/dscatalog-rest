package com.devsuperior.dscatalog.service;


import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.DTO.RoleDTO;
import com.devsuperior.dscatalog.DTO.UserDTO;
import com.devsuperior.dscatalog.DTO.UserInsertDTO;
import com.devsuperior.dscatalog.DTO.UserUpdateDTO;
import com.devsuperior.dscatalog.entities.Role;
import com.devsuperior.dscatalog.entities.User;
import com.devsuperior.dscatalog.repository.RoleRepository;
import com.devsuperior.dscatalog.repository.UserRepository;
import com.devsuperior.dscatalog.service.exception.DatabaseException;
import com.devsuperior.dscatalog.service.exception.ResourceEntityNotFoundException;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder; //vem la da classe AppConfig
	
	//listar todos
	@Transactional(readOnly = true)
	public Page<UserDTO> findAllPaged(PageRequest pageRequest){
		Page<User> list = repository.findAll(pageRequest);
		return list.map(x -> new UserDTO(x));
	}
	
	@Transactional(readOnly = true)
	public Page<UserDTO> findAllPages(Pageable pageable){
		Page<User> list = repository.findAll(pageable);
		return list.map(x -> new UserDTO(x));//não precisei usar o stream porque ele ja trabalha com page
	}
	
	//Procurar por id
	@Transactional(readOnly = true)
	public UserDTO findById(Long id){
		Optional<User> obj = repository.findById(id);
		User entity = obj.orElseThrow(()->new ResourceEntityNotFoundException("Não encontrado"));
		return new UserDTO(entity);
	}
	
	
	//salvar
	@Transactional
	public UserDTO salvar(UserInsertDTO dto) {//UserInsertDTO extende de User
		User entity = new User();
		//recebe o dto e a entidade
		copyDTOToEntity(dto, entity);
		entity.setPassword(passwordEncoder.encode(dto.getPassword()));
		entity = repository.save(entity);		
		return new UserDTO(entity);
	}
	
	
	//atualizar
	public UserDTO editar(Long id, UserUpdateDTO dto) {
		try {
			User entity = repository.getOne(id);//instacia uma unidade monitorada pelo JPA (Não vai no banco)
			//recebe o dto e a entidade
			copyDTOToEntity(dto, entity);
			entity.setPassword(passwordEncoder.encode(dto.getPassword()));
			entity = repository.save(entity);		
			return new UserDTO(entity);
		}catch(EntityNotFoundException e) {
			throw new ResourceEntityNotFoundException("Id não encontrado");
		}
	}
	
	//deletar
	public void deletar(Long id) {
		try {
			repository.deleteById(id);
		}catch(EmptyResultDataAccessException e) {
			throw new ResourceEntityNotFoundException("Id não encontrado "+id);
		}catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Integridade violada");
		}
		
	}
	
	//macete para armazanenar os dados para salvar e update
	private void copyDTOToEntity(UserDTO dto, User entity) { //nao setamos id porque 
		entity.setFirstName(dto.getFirstName());
		entity.setLastName(dto.getLastName());
		entity.setEmail(dto.getEmail());
		
		entity.getRoles().clear(); //acessa e limpa a coleção
		
		for(RoleDTO roleDTO : dto.getRoles()) {
			Role role = roleRepository.getOne(roleDTO.getId());
			entity.getRoles().add(role);//adicionando dentro da entidade de User	
		}
	}
	
	
	
	
	
	
}
