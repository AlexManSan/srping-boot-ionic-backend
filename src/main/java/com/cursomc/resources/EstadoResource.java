package com.cursomc.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cursomc.domain.Cidade;
import com.cursomc.domain.Estado;
import com.cursomc.dto.CidadeDTO;
import com.cursomc.dto.EstadoDTO;
import com.cursomc.services.CidadeService;
import com.cursomc.services.EstadoService;

@RestController
@RequestMapping(value="/estados")
public class EstadoResource {

	@Autowired
	private EstadoService service;
	
	@Autowired
	private CidadeService cidService;
	
	/**
	 * Lista os objetos
	 * @return
	 */
	@GetMapping
	public ResponseEntity<List<EstadoDTO>> findAll() { 
		List<Estado> list = service.findAll();
		// convertendo lista de estado em lista de estadoDTO
		List<EstadoDTO> listDto = list.stream().map(obj -> new EstadoDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}
	
	@GetMapping(value = "/{estadoId}/cidades")
	public ResponseEntity<List<CidadeDTO>> findCidades(@PathVariable Long estadoId) { 
		List<Cidade> list = cidService.findByEstado(estadoId);
		// convertendo lista de estado em lista de estadoDTO
		List<CidadeDTO> listDto = list.stream().map(obj -> new CidadeDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}
	
}
