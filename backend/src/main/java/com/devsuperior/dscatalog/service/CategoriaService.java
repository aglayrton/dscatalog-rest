package com.devsuperior.dscatalog.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.DTO.CategoriaDTO;
import com.devsuperior.dscatalog.entities.Categoria;
import com.devsuperior.dscatalog.repository.CategoriaRepository;
import com.devsuperior.dscatalog.service.exception.DatabaseException;
import com.devsuperior.dscatalog.service.exception.ResourceEntityNotFoundException;

@Service
public class CategoriaService{
	
	@Autowired
	private CategoriaRepository repository;
	
	@Transactional(readOnly = true)
	public List<CategoriaDTO> findAll(){
		List<Categoria> list =  repository.findAll();
		
		return list.stream().map(x -> new CategoriaDTO(x)).collect(Collectors.toList());
		
		/*List<CategoriaDTO> listDto = new ArrayList<>();
		for(Categoria cat : list) {
			listDto.add(new CategoriaDTO(cat));
				return listDto;
		}*/
	
	}
	
	@Transactional(readOnly = true)
	public CategoriaDTO findById(Long id) {
			Optional<Categoria> obj =  repository.findById(id);//efetiva o acesso ao banco
			Categoria entity = obj.orElseThrow(() -> new ResourceEntityNotFoundException("Entity not found"));
			return new CategoriaDTO(entity);
	}
	
	@Transactional
	public CategoriaDTO insert(CategoriaDTO dto) {
		Categoria cat = new Categoria();
		cat.setNome(dto.getName());
		cat = repository.save(cat);//ele retorna uma referencia para entidade salva
		return new CategoriaDTO(cat);
	}
	
	@Transactional
	public CategoriaDTO update(Long id, CategoriaDTO dto) {
		try{
			Categoria entity = repository.getOne(id);//ele instancia o objeto provisorio
			entity.setNome(dto.getName());
			entity = repository.save(entity); //salvando no banco
			return new CategoriaDTO(entity);//retornando como dto
		}catch (EntityNotFoundException e) {
			throw new ResourceEntityNotFoundException("Id não encontrado "+id);
		}
	}

	public void delete(Long id){
		try {
			repository.deleteById(id);
		}catch (EmptyResultDataAccessException e) {//caso nao tenha o id pra deletar
			throw new ResourceEntityNotFoundException("Id não encontrado "+id);
		}catch(DataIntegrityViolationException e) {//capturar a integridade
			throw new DatabaseException("Integridade violada");
		}
	}

	@Transactional(readOnly = true)
	public Page<CategoriaDTO> findAllPaged(PageRequest pageRequest){
		Page<Categoria> list =  repository.findAll(pageRequest);
		
		return list.map(x -> new CategoriaDTO(x));
		
	
	}
		
}
