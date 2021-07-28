package com.devsuperior.dscatalog.resource;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.devsuperior.dscatalog.DTO.ProdutoDTO;
import com.devsuperior.dscatalog.resources.ProdutoResource;
import com.devsuperior.dscatalog.service.ProdutoService;
import com.devsuperior.dscatalog.service.exception.ResourceEntityNotFoundException;
import com.devsuperior.dscatalog.test.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(ProdutoResource.class) //carrega o contexto somente para camada web
public class ProductResourceTest {
	
	//chamando os endpoints
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean //usar ele quando esta carregando o contexto ou somente camada web
	private ProdutoService service;
	
	private long existingId;
	private long noExistingId;
	private ProdutoDTO produtoDto;
	private PageImpl<ProdutoDTO> page;
	
	
	
	@BeforeEach
	void setup() throws Exception{
		existingId = 1L;
		noExistingId = 1000L;
		produtoDto = Factory.createdProdutoDto();
		
		//usamos o PageImpl porque ele aceita o new
		//criamos um objeto de pagina concreta
		page = new PageImpl<>(List.of(produtoDto));
		
		
		when(service.findAllPaged(ArgumentMatchers.any())).thenReturn(page);
	
		//quando no meu service eu chamar o finById
		when(service.findById(existingId)).thenReturn(produtoDto);
		when(service.findById(noExistingId)).thenThrow(ResourceEntityNotFoundException.class);
		

		//update
		Mockito.when(service.editar(ArgumentMatchers.eq(existingId), ArgumentMatchers.any())).thenReturn(produtoDto);
		Mockito.when(service.editar(ArgumentMatchers.eq(noExistingId), ArgumentMatchers.any())).thenThrow(EntityNotFoundException.class);
		
	}
	
	@Test
	public void updateShouldReturnProductWhenIdExisting() throws Exception {
		
		//convertendo java para texto (json) 
		String jsonBody = objectMapper.writeValueAsString(produtoDto);
		
		ResultActions result = mockMvc.perform(put("/produtos/editar/{id}", existingId)
				.content(jsonBody) //vai receber o dado
				.contentType(MediaType.APPLICATION_JSON)//e qual o tipo de dados
				.accept(MediaType.APPLICATION_JSON));//aqui é a resposta
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.id").exists());
	}
	
	@Test
	public void findByIdShoulReturnObjectWhenIdExisting() throws Exception {
		ResultActions result = mockMvc.perform(get("/produtos/{id}", existingId).accept(MediaType.APPLICATION_JSON));
			
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.id").exists()); //vê se achou o no corpo da resposta o campo id
		
	}
	
	@Test
	public void findByIdShoulNotFoundWhenNoIdExisting() throws Exception {
		ResultActions result = mockMvc.perform(get("/produtos/{id}", noExistingId).accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isNotFound());
	}
	
	@Test
	public void findAllShouldReturnPage() throws Exception{
		
		//perform faz uma requisicao  e no get é colocado o caminho
		//fazendo rquisicao no metodo http get, apos isso dizemos o que esperamos
		mockMvc.perform(get("/produtos/lista")).andExpect(status().isOk()); 
		//ou
		ResultActions result = mockMvc.perform(get("/produtos/lista"));
		result.andExpect(status().isOk());
		//negociacao do mediatype
		ResultActions result1 = mockMvc
				.perform(get("/produtos/lista")
				.accept(MediaType.APPLICATION_JSON)); //digo que estou retornando json nessa chamada
		result1.andExpect(status().isOk());//assertions
	}
	
	
}
