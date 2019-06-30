package com.cursomc.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cursomc.domain.Categoria;
import com.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService service;

//	@RequestMapping(method=RequestMethod.GET)
	@GetMapping
	public List<Categoria> listar() {
		System.out.println(">>>>> Rest lista de Categorias");
		
		Categoria cat1 = new Categoria((long) 1, "Informática");
		Categoria cat2 = new Categoria((long) 2, "Escritório");
		
		List<Categoria> lista = new ArrayList<Categoria>();
		lista.add(cat1);
		lista.add(cat2);
	
		return lista;
	}
	
	
	@GetMapping(value="/{id}")
	public ResponseEntity<?> find(@PathVariable Long id) {
//		try {
		
		System.out.println(">>>>> Rest busca uma Categoria por id");
		Categoria obj = service.buscar(id);
		return ResponseEntity.ok().body(obj);
		
//		}catch (Exception e) {
//			// TODO: handle exception
//		}
	}
	
	/**
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
}
