package com.devsuperior.dscatalog.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsuperior.dscatalog.DTO.CategoriaDTO;
import com.devsuperior.dscatalog.entities.Categoria;
import com.devsuperior.dscatalog.repository.CategoriaRepository;

@Service
@Transactional
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repository;
	
	public List<CategoriaDTO> findAll(){
		List<Categoria> list =  repository.findAll();
		
		return list.stream().map(x -> new CategoriaDTO(x)).collect(Collectors.toList());
		
		/*List<CategoriaDTO> listDto = new ArrayList<>();
		for(Categoria cat : list) {
			listDto.add(new CategoriaDTO(cat));
				return listDto;
		}*/
	
	}
	
	public CategoriaDTO findById(Long id){
		Optional<Categoria> obj =  repository.findById(id);
		Categoria entity = obj.orElseThrow(() -> new EntityNotFoundException("Entity not found"));
		return new CategoriaDTO(entity);
	}

	public CategoriaDTO insert(CategoriaDTO dto) {
		Categoria cat = new Categoria();
		cat.setNome(dto.getName());
		cat = repository.save(cat);//ele retorna uma referencia para entidade salva
		return new CategoriaDTO(cat);
	}
		
}
