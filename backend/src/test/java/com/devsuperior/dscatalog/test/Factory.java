package com.devsuperior.dscatalog.test;

import java.time.Instant;

import com.devsuperior.dscatalog.DTO.ProdutoDTO;
import com.devsuperior.dscatalog.entities.Categoria;
import com.devsuperior.dscatalog.entities.Produto;

public class Factory {
	
	public static Produto createdProduto() {
		Produto produto = new Produto(1L, "Phone", "Good", 800.0, "https:/img.com/img.png", Instant.parse("2021-07-24T03:00:00Z"));
		produto.getCategorias().add(new Categoria(1L, "Iphone"));
		return produto;
	}
	
	public static ProdutoDTO createdProdutoDto() {
		Produto produto = createdProduto();
		return new ProdutoDTO(produto, produto.getCategorias());
	}
	
}
