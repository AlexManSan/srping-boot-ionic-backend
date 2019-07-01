package com.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.cursomc.domain.Cliente;
import com.cursomc.dto.ClienteDTO;
import com.cursomc.exceptions.DataIntegrityException;
import com.cursomc.exceptions.ObjectNotFoundException;
import com.cursomc.repositories.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository dao;
	
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
	public Cliente insert(Cliente obj) {
		obj.setId(null); // se existir valor ao invés de criar será atualizado, logo forçando o novo
		return dao.save(obj);
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
			throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas");
		}		
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
	
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
	}
}
