package com.devsuperior.dscatalog.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.dscatalog.DTO.CategoriaDTO;
import com.devsuperior.dscatalog.service.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService service;
	
	@GetMapping("/lista")
	public ResponseEntity<Page<CategoriaDTO>> findAll(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "DESC") String direction
	){ 
		//
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		Page<CategoriaDTO> list = service.findAllPaged(pageRequest);
		
		return ResponseEntity.ok().body(list);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<CategoriaDTO> findAll(@PathVariable("id") Long id) {//um dado obrigatorio
		CategoriaDTO categoria = service.findById(id);
		return ResponseEntity.ok().body(categoria);
	}
	
	@PostMapping("/salvar")
	public ResponseEntity<CategoriaDTO> insert(@RequestBody CategoriaDTO dto){
		dto = service.insert(dto);
		//201 é de recurso criado por isso usamos esse comando junto com 
		//parametro no cabeçalho de resposta
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto); //vai retorna o 201
		//return ResponseEntity.ok().body(dto); retorna 200 como ok
	}
	
	
	@PutMapping("/editar/{id}")
	public ResponseEntity<CategoriaDTO> update(@PathVariable("id") Long id, @RequestBody CategoriaDTO dto) {
		dto = service.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}
	
	
	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build(); //Diz que a resposta deu certo 
	}
	
	
	
	
	
}
