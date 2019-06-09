package com.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cursomc.domain.Categoria;
import com.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository dao;
	
	public Categoria buscar(Long id) {
		// buscando por id e transformando em optional
		Optional<Categoria> obj = dao.findById(id); 
		
		// retona a categoria ou null 
		return obj.orElse(null);
	}
}
