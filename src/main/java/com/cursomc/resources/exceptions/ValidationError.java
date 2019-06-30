package com.cursomc.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {

	private static final long serialVersionUID = 1L;
	
	private List<FieldMessage> erros = new ArrayList<>();
	
	/**
	 * método Construtor
	 * @param status
	 * @param msg
	 * @param timeStamp
	 */
	public ValidationError(Integer status, String msg, Long timeStamp) {
		super(status, msg, timeStamp);
		
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
