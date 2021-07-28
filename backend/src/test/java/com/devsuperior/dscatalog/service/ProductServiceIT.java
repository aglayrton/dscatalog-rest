package com.devsuperior.dscatalog.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.DTO.ProdutoDTO;
import com.devsuperior.dscatalog.repository.ProdutoRepository;
import com.devsuperior.dscatalog.service.exception.ResourceEntityNotFoundException;

@Transactional //vai fazer co que cada transação tenha um rollback (pois executa 1 e depois volta ao normal para ex outo)
@SpringBootTest //Teste de integridade, traz todo o contexto, é mais lento
public class ProductServiceIT {
	
	@Autowired
	private ProdutoService service;
	
	@Autowired
	private ProdutoRepository repository;
	
	private Long existingId;
	private Long noExistingId;
	private Long countTotalProducts;
	

	@BeforeEach
	void setup() throws Exception{
		existingId = 1L;
		noExistingId = 1000l;
		countTotalProducts = 25l;
	}
	
	@Test
	public void findAllPagedShouldReturOrderedPageWhenSortByName() {
		
		PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("name"));
		
		Page<ProdutoDTO> result = service.findAllPaged(pageRequest);
		
		Assertions.assertFalse(result.isEmpty()); //tem quser verdadeiro o resultadoo vazio
		//achando o contenudo na posicao 0 com o nome tal
		Assertions.assertEquals("Macbook Pro", result.getContent().get(0).getName());
		Assertions.assertEquals("PC Gamer", result.getContent().get(1).getName());
		Assertions.assertEquals("PC Gamer Alfa", result.getContent().get(2).getName());
	}
	
	@Test
	public void findAllPagedShouldReturPageWhenPageDoesNotExist() {
		
		PageRequest pageRequest = PageRequest.of(50, 10);
		
		Page<ProdutoDTO> result = service.findAllPaged(pageRequest);
		
		Assertions.assertTrue(result.isEmpty()); //tem quser verdadeiro o resultadoo vazio
	
	}
	
	@Test
	public void findAllPagedShouldReturPageWhenPage0Size10() {
		
		PageRequest pageRequest = PageRequest.of(0, 10);
		
		Page<ProdutoDTO> result = service.findAllPaged(pageRequest);
		
		Assertions.assertFalse(result.isEmpty());
		
		//posso testar se a pagina realmente é 0
		Assertions.assertEquals(0, result.getNumber());
		Assertions.assertEquals(10, result.getSize());
		Assertions.assertEquals(countTotalProducts, result.getTotalElements());
	}
	
	//deveria(should) quando(when)
	@Test
	public void deleteShouldDeleteResourceWhenIdExists() {
		
		service.deletar(existingId);
		
		Assertions.assertEquals(countTotalProducts - 1, repository.count());
		
	}
	
	@Test
	public void deleteShouldThrowResourceEntityNotFoundExceptionDoesNotIdExists() {		
		Assertions.assertThrows(ResourceEntityNotFoundException.class, ()->{
			service.deletar(noExistingId);
		});
		
	}
	
}
