package com.devsuperior.dscatalog.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.dscatalog.DTO.ProdutoDTO;
import com.devsuperior.dscatalog.service.ProdutoService;

@RestController
@RequestMapping(("/produtos"))
public class ProdutoResource {

	@Autowired
	private ProdutoService service;

	@GetMapping("/lista")
	public ResponseEntity<Page<ProdutoDTO>> findAll(
			Pageable pageable
				
			/* @RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linePerPage", defaultValue = "12") Integer linePerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "name") String sort*/
			
	) {
		//Parametros: page, size, sort
		/*PageRequest produto = PageRequest.of(page, linePerPage, Direction.valueOf(direction), sort);
		Page<ProdutoDTO> dto = service.findAllPages(produto);*/
		
		Page<ProdutoDTO> dto = service.findAllPages(pageable);
		return ResponseEntity.ok().body(dto);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProdutoDTO> findById(@PathVariable("id") Long id) {
		ProdutoDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}

	@PostMapping("/salvar")
	public ResponseEntity<ProdutoDTO> salvar(@Valid @RequestBody ProdutoDTO dto) {
		dto = service.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PutMapping("/editar/{id}")
	public ResponseEntity<ProdutoDTO> salvar(@Valid @RequestBody ProdutoDTO dto, @PathVariable("id") Long id) {
		dto = service.editar(id, dto);
		return ResponseEntity.ok().body(dto);
	}
	
	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<Void> deletar(@PathVariable("id") Long id){
		service.deletar(id);
		return ResponseEntity.noContent().build();
	} 
	
	
}
