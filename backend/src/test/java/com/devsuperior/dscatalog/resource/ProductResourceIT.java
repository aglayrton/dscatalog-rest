package com.devsuperior.dscatalog.resource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.DTO.ProdutoDTO;
import com.devsuperior.dscatalog.test.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;

//usamos as duas annotations devido ser teste de integração e da camada web

@SpringBootTest
@AutoConfigureMockMvc 
@Transactional 
public class ProductResourceIT {

	@Autowired
	private MockMvc mockMvc;

	private Long existingId;
	private Long noExistingId;
	private Long countTotalProducts;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@BeforeEach
	void setup() {
		existingId = 1L;
		noExistingId = 1000l;
		countTotalProducts = 25l;
	}
	
	@Test
	public void updateShouldReturnProductWhenIdExisting() throws Exception {
		
		ProdutoDTO produtoDto = Factory.createdProdutoDto();
		
		
		//convertendo java para texto (json) 
		String jsonBody = objectMapper.writeValueAsString(produtoDto);
		
		String expectedName = produtoDto.getName();
		
		ResultActions result = mockMvc.perform(put("/produtos/editar/{id}", existingId)
				.content(jsonBody) //vai receber o dado
				.contentType(MediaType.APPLICATION_JSON)//e qual o tipo de dados
				.accept(MediaType.APPLICATION_JSON));//aqui é a resposta
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.name").value(expectedName)); //testo se  nome realmente igual aquele que coloquei pra atualizar
	}
	
	@Test
	public void findAllShouldReturnSortedPageWhenSortByName() throws Exception{
		ResultActions result = mockMvc.perform(get("/produtos/lista?page=0&size=12&sort=name,asc")
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.totalElements").value(countTotalProducts));
		result.andExpect(jsonPath("$.content").exists());
		
		result.andExpect(jsonPath("$.content[0].name").value("Macbook Pro"));
	}
	
	
}
