package com.cursomc.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe para tratamento de erros de validação de formulários,
 * Herdando de standardError
 * @author Alex
 *
 */
public class ValidationError extends StandardError {

	private static final long serialVersionUID = 1L;
	
	/**
	 * se eu instanciar a lista vazia ela não deve entrar no construtor
	 */
	private List<FieldMessage> erros = new ArrayList<>();
		
	/**
	 * método Construtor
	 * @param timestamp
	 * @param status
	 * @param error
	 * @param message
	 * @param path
	 * @param erros
	 */
	public ValidationError(Long timestamp, Integer status, String error, String message, String path) {
		super(timestamp, status, error, message, path);
	}

	public List<FieldMessage> getErros() {
		return erros;
	}

	/**
	 * Adiciona um errro de validação a lista
	 * @param fieldname
	 * @param message
	 */
	public void addError(String fieldname, String message) {
		erros.add(new FieldMessage(fieldname, message));
	}
	
	

}
