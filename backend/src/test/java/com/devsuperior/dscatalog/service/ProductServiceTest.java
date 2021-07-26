package com.devsuperior.dscatalog.service;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.dscatalog.DTO.ProdutoDTO;
import com.devsuperior.dscatalog.entities.Produto;
import com.devsuperior.dscatalog.repository.ProdutoRepository;
import com.devsuperior.dscatalog.service.exception.DatabaseException;
import com.devsuperior.dscatalog.service.exception.ResourceEntityNotFoundException;
import com.devsuperior.dscatalog.test.Factory;

//fazendo o teste de unidade (testa somente aquela classe especifica sem carregar o outro componente do que ela depende)

@ExtendWith(SpringExtension.class) // nao carrega o contexto
public class ProductServiceTest {

	@InjectMocks // se fosse autowired seria a injecao normal
	private ProdutoService service;

	// Mock mais usado quando nao carrega um contexto
	// acima precisa do repository, so que nao injetamos, criamos um objeto mocado
	// (simula)
	@Mock // quando faz um mock tem que configurar o comportamento simulado
	private ProdutoRepository repository;

	private long existingId;
	private long noExistingId;
	private long dependentId;
	private PageImpl<Produto> page;
	private Produto produto;


	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		noExistingId = 1000L;
		dependentId = 4L;
		produto = Factory.createdProduto();
		page = new PageImpl<>(List.of(produto));
		
		//AQUI DENTRO JA ESTAMOS COLOCANDO O COMPORTAMENTO DOS MÉTODOS QUANDO CHAMADOS NOS TESTES
		
		//(Pageable)ArgumentMatchers.any() é o argumento qualquer, vai fazer then
		Mockito.when(repository.findAll((Pageable)ArgumentMatchers.any())).thenReturn(page);
		
		Mockito.when(repository.save(produto)).thenReturn(produto);
		
		Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(produto);
		
		//simulando um cenário com id existente retornando um objeto optional
		Mockito.when(repository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(produto));
		
		//simulando um cenário com id não existente
		Mockito.when(repository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());
		
		// configurando,
		// o when é pra chamar algum método mocado que retorna alguma coisa
		// abaixo: quando eu chamar o deleteById com id existente o método nao vai fazer
		// nada
		Mockito.doNothing().when(repository).deleteById(existingId);

		// Para que chame a exceção caso o id nao exista
		Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(noExistingId);

		Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
	}
	
	/*findById
	 * Retornar um ProdutoDTO quando o id existir*/
	@Test
	public void produtoDTOShouldIdExisting() {
		
		ProdutoDTO result = service.findById(existingId);
		
		Assertions.assertNotNull(result);
		
		Mockito.verify(repository).findById(existingId);
		
	}
	

	@Test
	public void ShouldResourceNotFoundExceptioWhenIdNotExisting() {
		
		
		Assertions.assertThrows(ResourceEntityNotFoundException.class, ()->{
			service.findById(noExistingId);
		});
		
		Mockito.verify(repository).findById(noExistingId);
		
	}
	
	
	
	@Test
	public void findAllPagedShouldReturnPage() {
		Pageable pageable = PageRequest.of(0, 10);
		
		Page<ProdutoDTO> result = service.findAllPages(pageable);
		
		Assertions.assertNotNull(result);//tem que da verdadeiro
		
		Mockito.verify(repository, Mockito.times(1)).findAll(pageable);//tem que da verdadeiro
	}
	
	// testando delete lanca databaseexception quando id dependente
	@Test
	public void deleteShouldThrowDatabaseExceptionWhenIdNotExists() {

		Assertions.assertThrows(DatabaseException.class, () -> {
			service.deletar(dependentId);
		});

		Mockito.verify(repository, Mockito.times(1)).deleteById(dependentId);

	}

	// testando quando o id nao exisitr para lancar a
	// ResourceEntityNotFoundException
	@Test
	public void deleteShouldThrowResourceEntityNotFoundExceptionWhenIdNotExists() {

		Assertions.assertThrows(ResourceEntityNotFoundException.class, () -> {
			service.deletar(noExistingId);
		});

		Mockito.verify(repository).deleteById(noExistingId);

	}

	@Test
	public void deleteShouldDoNothingWhegnIdExists() {

		// nao vai ter excecao nenhuma - so lembrando que esta sendo chamado o deletar
		// do Mockito
		// o que foi configurado la em cima, ou seja, faz nada.
		Assertions.assertDoesNotThrow(() -> {
			service.deletar(existingId);
		});

		// verify verifica se alguma chamada foi feita
		// quando jogar o método mocado, quando se coloca o ponto, ele vai trazer todos
		// os métodos daquele repository
		Mockito.verify(repository).deleteById(existingId);

		// numero de vezes que ele chama - pode ser usado sem chamar o nome da Classe
		// verify(repository, Mockito.times(1)).deleteById(existingId);

	}
}
