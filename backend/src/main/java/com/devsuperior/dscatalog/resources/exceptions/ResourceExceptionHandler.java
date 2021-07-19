package com.devsuperior.dscatalog.resources.exceptions;

import java.time.Instant;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice //permiti que a classe intercepte exceções
public class ResourceExceptionHandler{

	//método para tratar a exceção 	//recebe uma exceção e a requisição
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<StandarError> entityNotFound(EntityNotFoundException e, HttpServletRequest req){
		StandarError err = new StandarError();
		err.setTimestamp(Instant.now()); //pega o instante atual
		err.setStatus(HttpStatus.NOT_FOUND.value()); //pega o valor do erro
		err.setError("Resource not found");
		err.setMessage(e.getMessage());//pega a mensagem do erro
		err.setPath(req.getRequestURI());//pega o caminho requisitado
		//customiza  o que vamos retornar
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
	
}
