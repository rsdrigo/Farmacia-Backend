package com.generation.Crud_farmacia.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.generation.Crud_farmacia.model.Categoria;
import com.generation.Crud_farmacia.repository.CategoriaRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CategoriaControllerTest {

	@Autowired
	private TestRestTemplate testeRestTemplate;
		
	@Autowired
	private CategoriaRepository categoriaRepository;

	@Test
	@DisplayName("Cadastrar uma Categoria")
	public void deveCriarUmaCategoria() {

		HttpEntity<Categoria> corpoRequisicao = new HttpEntity<Categoria>(
				new Categoria(0L, "Vacinas", "Vacinas em geral"));

		ResponseEntity<Categoria> corpoResposta = testeRestTemplate.exchange("/categorias", HttpMethod.POST,
				corpoRequisicao, Categoria.class);

		assertEquals(HttpStatus.CREATED, corpoResposta.getStatusCode());
	}
		
	@Test
	@DisplayName("Atualizar uma Categoria")
	public void deveAtualizarUmaCategoria() {
		
		Categoria categoriaCadastrada = categoriaRepository.save(new Categoria(0L, 
				"Vacinas", "Vacinas em geral"));
		
		Categoria categoriaUpdate = new Categoria(categoriaCadastrada.getId(),
				"Vacinas", "Vacinas de todos os tipos");
		
		HttpEntity<Categoria> corpoRequisicao = new HttpEntity<Categoria>(categoriaUpdate);
		
		ResponseEntity<Categoria> corpoResposta = testeRestTemplate
				.exchange("/categorias", HttpMethod.PUT, corpoRequisicao, Categoria.class);
		
		assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());
	}
	
	@Test
	@DisplayName("Listar todas as categorias")
	public void deveMostrarTodasCategorias() {
	    	    
	    Categoria categoria = categoriaRepository.save(new Categoria(0L,
	    		"Vacinas", "Vacinas em geral"));
	    Categoria categoria2 = categoriaRepository.save(new Categoria(0L,
	    		"Gases", "Gases em geral"));
	    	    
	    ResponseEntity<String> resposta = testeRestTemplate
	    		.exchange("/categorias", HttpMethod.GET, null, String.class);
	    
	        assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}
}
