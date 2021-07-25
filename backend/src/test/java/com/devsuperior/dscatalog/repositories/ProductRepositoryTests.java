package com.devsuperior.dscatalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.devsuperior.dscatalog.entities.Produto;
import com.devsuperior.dscatalog.repository.ProdutoRepository;
import com.devsuperior.dscatalog.test.Factory;

//colocamos o mesmo nome de pacote para termos acessos

@DataJpaTest //testa somente os repositorys - carrega o repository e o banco que ta sendo trabalhado se baseando no repository
public class ProductRepositoryTests {

	@Autowired
	private ProdutoRepository repository;
	
	private long existingId;
	private long noExistingId;
	private long countTotalProdutos;
	
	//antes de cada teste eu já inicializao as variaveis
	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		noExistingId = 1000L;
		countTotalProdutos = 25L;
	}
	
	@Test 
	public void saveShouldPersistWithAutoIncrement() {
		Produto produto = Factory.createdProduto();
		produto.setId(null);
		
		produto = repository.save(produto);
		
		Assertions.assertNotNull(produto.getId());
		Assertions.assertEquals(countTotalProdutos + 1, produto.getId());
	}
	
	
	@Test
	public void deleteShouldObjectWhenIdExists() {
		
		//Long existingId = 1L;
		
		repository.deleteById(existingId);
		
		Optional<Produto> result = repository.findById(1L); //carinha retorna um optional
		
		
		Assertions.assertFalse(result.isPresent()); //isPresent verifica se o objeto existe
	}
	
	//pra da ok quando nao existir o dado que nao existe
	@Test
	public void deleteShouldThrowEmptyResultDataAcessExceptionWhenIdDoesNotExist() {
		
		//Long noExistingId = 1000L;
		
		Assertions.assertThrows(EmptyResultDataAccessException.class, ()->{
			repository.deleteById(noExistingId);
		}); 
	}
	
	public void findByShouldReturnNoEmptyWhenExisting() {
		Optional<Produto> result = repository.findById(existingId);
		
		Assertions.assertTrue(result.isPresent());
		
	}
	
	public void findByShouldNotReturnNoEmptyWhenExisting() {
		Optional<Produto> result = repository.findById(existingId);
		
		Assertions.assertTrue(result.isEmpty());
		
	}
}
