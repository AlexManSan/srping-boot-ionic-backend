package com.cursomc.exceptions;

public class DataIntegrityException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Construtor
	 * @param msg
	 */
	public DataIntegrityException(String msg) {
		super(msg);
	}
	
	/** 
	 * Outro construtor com a causa do problema
	 * @param msg
	 * @param cause
	 */
	public DataIntegrityException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
