package com.cursomc.exceptions;

public class AuthorizationException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Construtor
	 * @param msg
	 */
	public AuthorizationException(String msg) {
		super(msg);
	}
	
	/** 
	 * Outro construtor com a causa do problema
	 * @param msg
	 * @param cause
	 */
	public AuthorizationException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
