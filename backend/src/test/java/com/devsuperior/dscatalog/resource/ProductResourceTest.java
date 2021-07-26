package com.devsuperior.dscatalog.resource;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;

import com.devsuperior.dscatalog.DTO.ProdutoDTO;
import com.devsuperior.dscatalog.resources.ProdutoResource;
import com.devsuperior.dscatalog.service.ProdutoService;
import com.devsuperior.dscatalog.test.Factory;


@WebMvcTest(ProdutoResource.class) //carrega o contexto somente para camada web
public class ProductResourceTest {
	
	//chamando os endpoints
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean //usar ele quando esta carregando o contexto ou somente camada web
	private ProdutoService service;
	
	private ProdutoDTO produtoDto;
	private PageImpl<ProdutoDTO> page;
	
	@BeforeEach
	void setup() throws Exception{
		
		produtoDto = Factory.createdProdutoDto();
		
		//usamos o PageImpl porque ele aceita o new
		//criamos um objeto de pagina concreta
		page = new PageImpl<>(List.of(produtoDto));
		
		
		when(service.findAllPaged(ArgumentMatchers.any())).thenReturn(page);
	}
	
	@Test
	public void findAllShouldReturnPage() throws Exception{
		
		//perform faz uma requisicao  e no get é colocado o caminho
		//fazendo rquisicao no metodo http get, apos isso dizemos o que esperamos
		mockMvc.perform(get("/produtos/lista")).andExpect(status().isOk());
	}
	
	
	
}
