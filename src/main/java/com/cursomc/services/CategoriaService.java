package com.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.cursomc.domain.Categoria;
import com.cursomc.exceptions.DataIntegrityException;
import com.cursomc.exceptions.ObjectNotFoundException;
import com.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository dao;
	
	/**
	 * Método busca o objeto no banco e dispara um excessão caso não exista
	 * @param id
	 * @return
	 */
	public Categoria find(Long id) {
		// buscando por id e transformando em optional
		Optional<Categoria> obj = dao.findById(id); 
		
		// retona a categoria ou uma exceção caso o id não exista 
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	
	/**
	 * Cria um novo objeto 
	 * @param obj
	 * @return
	 */
	public Categoria insert(Categoria obj) {
		obj.setId(null); // se existir valor ao invés de criar será atualizado, logo forçando o novo
		return dao.save(obj);
	}
	
	/**
	 * Atualiza o objeto 
	 * @param obj
	 * @return
	 */
	public Categoria update(Categoria obj) {
		find(obj.getId());
		return dao.save(obj);
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
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
		}		
	}
}
