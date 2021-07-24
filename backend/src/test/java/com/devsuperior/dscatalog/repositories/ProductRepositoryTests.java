package com.devsuperior.dscatalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.devsuperior.dscatalog.entities.Produto;
import com.devsuperior.dscatalog.repository.ProdutoRepository;

//colocamos o mesmo nome de pacote para termos acessos

@DataJpaTest //testa somente os repositorys - carrega o repository e o banco que ta sendo trabalhado se baseando no repository
public class ProductRepositoryTests {

	@Autowired
	private ProdutoRepository repository;
	
	@Test
	public void deleteShouldObjectWhenIdExists() {
		
		Long existingId = 1L;
		
		repository.deleteById(existingId);
		
		Optional<Produto> result = repository.findById(1L); //carinha retorna um optional
		
		
		Assertions.assertFalse(result.isPresent()); //isPresent verifica se o objeto existe
	}
	
	@Test
	public void deleteShouldThrowEmptyResultDataAcessExceptionWhenIdDoesNotExist() {
		
		Long noExistingId = 1000L;
		
		Assertions.assertThrows(EmptyResultDataAccessException.class, ()->{
			repository.deleteById(noExistingId);
		}); 
	}
	
}
