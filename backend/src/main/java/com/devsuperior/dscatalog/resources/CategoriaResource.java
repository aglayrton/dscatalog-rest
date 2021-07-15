package com.devsuperior.dscatalog.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dscatalog.entities.Categoria;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {
	
	@GetMapping("/lista")
	public ResponseEntity<List<Categoria>> findAll(){
		List<Categoria> list = new ArrayList<>();
		list.add(new Categoria(1L, "Livros"));
		list.add(new Categoria(1L, "Eletronicos"));
		return ResponseEntity.ok().body(list);
	}
	
}
