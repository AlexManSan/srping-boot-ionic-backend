package com.cursomc.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	/**
	 * PEsquisa um Objeto com o id
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
