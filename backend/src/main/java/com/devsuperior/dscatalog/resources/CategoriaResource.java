package com.devsuperior.dscatalog.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dscatalog.DTO.CategoriaDTO;
import com.devsuperior.dscatalog.service.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService service;
	
	@GetMapping("/lista")
	public ResponseEntity<List<CategoriaDTO>> findAll(){
		List<CategoriaDTO> list = service.findAll();
		
		return ResponseEntity.ok().body(list);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<CategoriaDTO> findAll(@PathVariable("id") Long id){
		CategoriaDTO categoria = service.findById(id);
		return ResponseEntity.ok().body(categoria);
	}
}
