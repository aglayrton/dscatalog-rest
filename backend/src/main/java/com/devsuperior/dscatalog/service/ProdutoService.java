package com.devsuperior.dscatalog.service;


import java.sql.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.DTO.CategoriaDTO;
import com.devsuperior.dscatalog.DTO.ProdutoDTO;
import com.devsuperior.dscatalog.entities.Categoria;
import com.devsuperior.dscatalog.entities.Produto;
import com.devsuperior.dscatalog.repository.CategoriaRepository;
import com.devsuperior.dscatalog.repository.ProdutoRepository;
import com.devsuperior.dscatalog.service.exception.DatabaseException;
import com.devsuperior.dscatalog.service.exception.ResourceEntityNotFoundException;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	//listar todos
	@Transactional(readOnly = true)
	public Page<ProdutoDTO> findAllPaged(PageRequest pageRequest){
		Page<Produto> list = repository.findAll(pageRequest);
		return list.map(x -> new ProdutoDTO(x));
	}
	
	@Transactional(readOnly = true)
	public Page<ProdutoDTO> findAllPages(Long categoryId, String name, Pageable pageable){
		List<Categoria> categoria = (categoryId == 0) ? null : Arrays.asList(categoriaRepository.getOne(categoryId));
		
		Page<Produto> list = repository.find(categoria, name, pageable);
		return list.map(x -> new ProdutoDTO(x));
	}
	
	//Procurar por id
	@Transactional(readOnly = true)
	public ProdutoDTO findById(Long id){
		Optional<Produto> obj = repository.findById(id);
		Produto produto = obj.orElseThrow(()->new ResourceEntityNotFoundException("Não encontrado"));
		return new ProdutoDTO(produto, produto.getCategorias());
	}
	
	
	//salvar
	@Transactional
	public ProdutoDTO salvar(ProdutoDTO dto) {
		Produto entity = new Produto();
		//recebe o dto e a entidade
		copyDTOToEntity(dto, entity);
		entity = repository.save(entity);		
		return new ProdutoDTO(entity);
	}
	
	
	//atualizar
	public ProdutoDTO editar(Long id, ProdutoDTO dto) {
		try {
			Produto entity = repository.getOne(id);
			//recebe o dto e a entidade
			copyDTOToEntity(dto, entity);
			entity = repository.save(entity);		
			return new ProdutoDTO(entity);
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
	private void copyDTOToEntity(ProdutoDTO dto, Produto entity) { //nao setamos id porque 
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setDate(dto.getDate());
		entity.setImgURL(dto.getImgUrl());
		entity.setPrice(dto.getPrice());
		
		entity.getCategorias().clear(); //acessa as categorias e limpa
		
		for(CategoriaDTO catDTO : dto.getCategorias()) {
			//VAMOS PROCURAR A CATEGORIA PELO ID
			//Dai instanciamos a categoria
			Categoria categoria = categoriaRepository.getOne(catDTO.getId());
			entity.getCategorias().add(categoria);//adicionando dentro da entidade de produto	
		}
	}
	
	
	
	
	
	
}
