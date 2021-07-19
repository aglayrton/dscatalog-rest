package com.devsuperior.dscatalog.DTO;

import com.devsuperior.dscatalog.entities.Categoria;

public class CategoriaDTO {
	private Long id;
	private String name;
	
	public CategoriaDTO() {
	}
	
	public CategoriaDTO(Categoria categoria) {
		this.id = categoria.getId();
		this.name = categoria.getNome();
	}
	
	public CategoriaDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
