package com.devsuperior.dscatalog.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.dscatalog.repository.ProdutoRepository;

@ExtendWith(SpringExtension.class)
public class ProductServiceTest {
	
	@InjectMocks
	private ProdutoService service;
	
	//quando faz um mock tem que configurar o comportamento simulado
	@Mock 
	private ProdutoRepository repository;
	
	private long existingId;
	private long noExistingId;
	
	@BeforeEach
	void setUp() throws Exception{
		existingId = 1L;
		noExistingId = 1000L;
		
		//configurando, 
		//o when é pra chamar algum método  mocado que retorna alguma coisa
		//abaixo: quando eu chamar o deleteById com id existente o método nao vai fazer nada
		Mockito.doNothing().when(repository).deleteById(existingId);
		
		//Para que chame a exceção caso o id nao exista
		Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(noExistingId);
	}
	
	@Test
	public void deleteShouldDoNothingWhegnIdExists() {
		
		//nao vai ter excecao nenhuma - so lembrando que esta sendo chamado o deletar do Mockito
		//o que foi configurado la em cima, ou seja, faz nada.
		Assertions.assertDoesNotThrow(() -> {
			service.deletar(existingId);
		});
		
		//verifica se alguma chamada foi feita
		//comando abaixo da acesso a todos os métodos
		Mockito.verify(repository).deleteById(existingId);
		
		//numero de vezes que ele chama
		Mockito.verify(repository, Mockito.times(2)).deleteById(existingId);
		
	}
}
