package com.devsuperior.dscatalog.service.exception;



//se fosse exception obrigariamos a todas as exceções serem tratadas
public class ResourceEntityNotFoundException extends RuntimeException{//

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResourceEntityNotFoundException(String msg) {
		super(msg);
	}
	
}
