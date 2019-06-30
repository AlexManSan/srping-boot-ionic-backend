package com.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cursomc.domain.Categoria;
import com.cursomc.exceptions.ObjectNotFoundException;
import com.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository dao;
	
	public Categoria buscar(Long id) {
		// buscando por id e transformando em optional
		Optional<Categoria> obj = dao.findById(id); 
		
		// retona a categoria ou uma exceção caso o id não exista 
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));

	}
	
	/**
	 * Cria uma nova categoria 
	 * @param obj
	 * @return
	 */
	public Categoria insert(Categoria obj) {
		obj.setId(null); // se existir valor ao invés de criar será atualizado, logo forçando o novo
		return dao.save(obj);
	}
}
