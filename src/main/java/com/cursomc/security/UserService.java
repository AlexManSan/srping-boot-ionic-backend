package com.cursomc.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class UserService {
	
	/**
	 * Retorna o usuário logado no sistema
	 * @return
	 */
	public static UserSpringSecurity autenticado() {
		try {
			return (UserSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		// serve para qualquer exceção
		catch (Exception e) {
			return null;
		}
	}

}
