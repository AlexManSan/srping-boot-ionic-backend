package com.cursomc.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cursomc.domain.enums.Perfil;

/**
 * Classe responsavel por implementar o contrato userDetails do Srping Scurity
 * @author Alex
 *
 */
public class UserSpringSecurity implements UserDetails{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String email;
	private String senha;
	
	// perfis
	private Collection<? extends GrantedAuthority> authorities;
	
	/*Construtores*/
	public UserSpringSecurity() {}
		
	public UserSpringSecurity(Long id, String email, String senha, Set<Perfil> perfis) {
		super();
		this.id = id;
		this.email = email;
		this.senha = senha;
		// transformei a lista de perfis em GrantedAuthority
		this.authorities = perfis.stream().map(x -> new SimpleGrantedAuthority(x.getDescricao())).collect(Collectors.toList());
	}



	public Long getId() {
		return id;
	}

	// são os perfis dos usuarios
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	/**
	 * Interessante
	 * @param perfil
	 * @return
	 */
	public boolean possuiPerfil(Perfil perfil) {
		return getAuthorities().contains(new SimpleGrantedAuthority(perfil.getDescricao()));
	}

}
