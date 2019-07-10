package com.cursomc.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter{
	
	private JWTUtil jwtUtil;
	private UserDetailsService userDetailsService; // para fazer a busca do usuario por email

	/**
	 * Construtor
	 * @param authenticationManager
	 * @param jwtUtil
	 * @param userDetailService
	 */
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, UserDetailsService userDetailsService) {
		super(authenticationManager);
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
	}
	
	/**
	 * pego o valor do cabeçalho
	 */
	@Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
		//pego o valor do cabeçalho
		String header = request.getHeader("Authorization");
		// verifico se contem um Bearer+espaço
		if (header != null && header.startsWith("Bearer ")) {
			//capturo o tokem sem o bearer através do método abaixo
			UsernamePasswordAuthenticationToken auth = getAuthentication(header.substring(7));
			
			if (auth != null) {
				// está ok e libera o acesso ao usuário
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}
		// pode continuar fazendo as requisições
		chain.doFilter(request, response);
	}

	/**
	 * Recebe um token capturado do cabeçalho da requisição e verifica se o token é valido
	 * @param token
	 * @return
	 */
	private UsernamePasswordAuthenticationToken getAuthentication(String token) {
		// verifica se o token é valido e captura o login e o userDetails
		if (jwtUtil.tokenValido(token)) {
			String username = jwtUtil.getUsername(token);
			UserDetails user = userDetailsService.loadUserByUsername(username);
			return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		}
		return null;
	}

}
