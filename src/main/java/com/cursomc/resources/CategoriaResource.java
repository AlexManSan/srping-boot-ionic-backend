package com.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cursomc.domain.Categoria;
import com.cursomc.dto.CategoriaDTO;
import com.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService service;


	/**
	 * Lista os objetos
	 * @return
	 */
	@GetMapping
	public ResponseEntity<List<CategoriaDTO>> findAll() { 
		List<Categoria> list = service.findAll();
		/* lista de CategoriaDTO, stream()= varre uma lista; map= efetua uma operação para cada elemento da lista; 
		 * collect(Collectors.toList()) transforma em na lista nova do objeto novo */
		List<CategoriaDTO> listDto = list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}
	
	/**
	 * Lista com paginação
	 * @param page
	 * @param linesPerPage
	 * @param orderBy
	 * @param direction
	 * @return
	 */
	// categorias/page?page=0&linesPerPage=20 ....
	@GetMapping(value="/page")
	public ResponseEntity<Page<CategoriaDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) { 
		
		Page<Categoria> list = service.findPage(page, linesPerPage, orderBy, direction);
		/* lista de CategoriaDTO, stream()= varre uma lista; map= efetua uma operação para cada elemento da lista; 
		 * collect(Collectors.toList()) transforma em na lista nova do objeto novo */
		Page<CategoriaDTO> listDto = list.map(obj -> new CategoriaDTO(obj));
		return ResponseEntity.ok().body(listDto);
	}
	
	/**
	 * Pesquisa um Objeto com o id
	 * @param id
	 * @return
	 */
	@GetMapping(value="/{id}")
	public ResponseEntity<Categoria> find(@PathVariable Long id) {
//		try {
		
		System.out.println(">>>>> Rest busca uma Categoria por id");
		Categoria obj = service.find(id);
		return ResponseEntity.ok().body(obj);
		
//		}catch (Exception e) {
//			// TODO: handle exception
//		}
	}
	
	/**
	 * Cria um Objeto 
	 * void corpo vazio, @RequestBody já converte o json recebido em obj categoria
	 * @param obj
	 * @return
	 */
	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody Categoria obj) {
		obj = service.insert(obj);
		
		// devolvendo a uri do objeto criado
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(obj.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	/**
	 * Atualiza um objeto
	 * @param obj
	 * @param id
	 * @return
	 */
	@PutMapping(value="/{id}")
	public ResponseEntity<Void> update(@RequestBody Categoria obj, @PathVariable Long id) {
		obj.setId(id); // pra garantir que o id é o mesmo recebido
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	/**
	 * Deleta um objeto
	 * @param obj
	 * @param id
	 * @return
	 */
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
