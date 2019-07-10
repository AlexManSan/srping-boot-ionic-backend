package com.cursomc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cursomc.domain.Cliente;
import com.cursomc.repositories.ClienteRepository;

/**
 * Serviço responsavel por implementar o userdetailservice
 * @author Alex
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private ClienteRepository dao;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// fazer a busca por email
		Cliente cli = dao.findByEmail(email);
		if(cli == null) {
			//crio uma exceção do UserDetailsServiceImpl
			throw new UsernameNotFoundException("Email não existe: " + email);
		}		
		return new UserSpringSecurity(cli.getId(), cli.getEmail(), cli.getSenha(), cli.getPerfis());
	}

}
