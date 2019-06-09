package com.cursomc.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cursomc.domain.Categoria;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {

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
}
