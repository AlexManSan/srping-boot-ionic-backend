package com.cursomc.exceptions;

public class ObjectNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Construtor
	 * @param msg
	 */
	public ObjectNotFoundException(String msg) {
		super(msg);
	}
	
	/** 
	 * Outro construtor com a causa do problema
	 * @param msg
	 * @param cause
	 */
	public ObjectNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
