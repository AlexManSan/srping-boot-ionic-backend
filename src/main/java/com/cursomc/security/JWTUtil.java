package com.cursomc.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component // permite ser injetada em outras classes como component
public class JWTUtil {

	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private Long expiration;
	
	/**
	 * Gera o token para o sistema
	 * @param username
	 * @return
	 */
	public String generateToken(String username) {
		return Jwts.builder()
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())
				.compact();
	}
	
	/**
	 * Recebe um token e verifica se o mesmo é válido
	 * @param token
	 * @return
	 */
	public boolean tokenValido(String token) {
		Claims claims = getClaims(token);
		if (claims != null) {
			String username = claims.getSubject();
			// testa se o token está expirado
			Date expirationDate = claims.getExpiration();
			Date now = new Date(System.currentTimeMillis());
			// checa se expirou
			if (username != null && expirationDate != null && now.before(expirationDate)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Recebe um token e extrai o usuario que fez o login que neste caso é o email
	 * @param token
	 * @return
	 */
	public String getUsername(String token) {
		Claims claims = getClaims(token);
		if (claims != null) {
			return claims.getSubject();
		}
		return null;
	}

	/**
	 * método usado no getusername para capturar o nome de login
	 * é onde contém os dados de 
	 * @param token
	 * @return
	 */
	private Claims getClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
		}
		catch (Exception e) {
			return null;
		}
	}
}
