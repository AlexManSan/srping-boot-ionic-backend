package com.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cursomc.domain.Cidade;
import com.cursomc.domain.Cliente;
import com.cursomc.domain.Endereco;
import com.cursomc.domain.enums.Perfil;
import com.cursomc.domain.enums.TipoCliente;
import com.cursomc.dto.ClienteDTO;
import com.cursomc.dto.ClienteNewDTO;
import com.cursomc.exceptions.AuthorizationException;
import com.cursomc.exceptions.DataIntegrityException;
import com.cursomc.exceptions.ObjectNotFoundException;
import com.cursomc.repositories.ClienteRepository;
import com.cursomc.repositories.EnderecoRepository;
import com.cursomc.security.UserService;
import com.cursomc.security.UserSpringSecurity;

@Service
public class ClienteService {
	
	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private ClienteRepository dao;
	
	@Autowired
	private EnderecoRepository endDao;
	
	/**
	 * Método lista todos os objetos
	 * @return
	 */
	public List<Cliente> findAll(){
		return dao.findAll();
	}
	
	/**
	 * Método busca o objeto no banco e dispara um excessão caso não exista
	 * @param id
	 * @return
	 */
	public Cliente find(Long id) {
		// pegando usuario logado
		UserSpringSecurity user = UserService.autenticado();
		if(user == null || !user.possuiPerfil(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado, usuário não possui permissão");
		}
		
		// buscando por id e transformando em optional
		Optional<Cliente> obj = dao.findById(id); 
		
		// retona a categoria ou uma exceção caso o id não exista 
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	/**
	 * Cria um novo objeto 
	 * @param obj
	 * @return
	 */
	@Transactional // garante que vai salvar tanto o cliente quanto os endereços na mesma transação do banco de dados
	public Cliente insert(Cliente obj) {
		obj.setId(null); // se existir valor ao invés de criar será atualizado, logo forçando o novo
		obj = dao.save(obj);
		endDao.saveAll(obj.getEnderecos());
		return obj;
	}
	
	/**
	 * Atualiza o objeto 
	 * @param obj
	 * @return
	 */
	public Cliente update(Cliente obj) {
		Cliente objbanco = find(obj.getId());
		updateData(objbanco, obj);
		
		return dao.save(objbanco);
	}
	
	/**
	 * Atualiza somente os valores novos vindo do DTO
	 * @param objbanco
	 * @param obj
	 */
	private void updateData(Cliente objbanco, Cliente obj) {
		objbanco.setNome(obj.getNome());
		objbanco.setEmail(obj.getEmail());
	}

	/**
	 * Deleta um objeto
	 * @param obj
	 */
	public void delete(Long id) {
		find(id);
		try {
			dao.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			// personalizo a minha exception
			throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionadas");
		}		
	}
	
	/**
	 * Busca um Cliente por email
	 * @param email
	 * @return
	 */
	public Cliente findByEmail(String email) {
		UserSpringSecurity user = UserService.autenticado();
		if(user == null || !user.possuiPerfil(Perfil.ADMIN) && !email.equals(user.getUsername())) {
			throw new AuthorizationException("Acesso Negado");
		}
		Cliente obj = dao.findByEmail(email);
		if(obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: " +user.getId() + ", Tipo: " + Cliente.class.getName());
		}
		return obj;
	}

	/**
	 * Método que retorna informações por página.
	 * @param page
	 * @param linesPerPage
	 * @param orderBy
	 * @param direction
	 * @return
	 */
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return dao.findAll(pageRequest);
	}
	
	/**
	 * Recebe um objDto e retorna um cliente simples
	 * @param objDto
	 * @return
	 */
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null, null);
	}
	
	/**
	 * Recebe um objDto e retorna um cliente Completo
	 * @param objDto
	 * @return
	 */
	public Cliente fromDTO(ClienteNewDTO objDto) {
		 Cliente c = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()), pe.encode(objDto.getSenha()) );
		 Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		 Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getCoplemento(), objDto.getBairro(), objDto.getCep(), c, cid);
		 
		 c.getEnderecos().add(end);
		 c.getTelefones().add(objDto.getTelefone1());
		 
		 if(objDto.getTelefone2() != null) {
			 c.getTelefones().add(objDto.getTelefone2());
		 }
		 if(objDto.getTelefone3() != null) {
			 c.getTelefones().add(objDto.getTelefone3());
		 }
		 return c;
	}
}
