package com.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cursomc.domain.Cidade;
import com.cursomc.repositories.CidadeRepository;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository dao;
	
	public List<Cidade> findByEstado(Long estadoId) {
		return dao.findCidades(estadoId);
	}
}
