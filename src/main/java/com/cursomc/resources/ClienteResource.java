package com.cursomc.resources;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cursomc.domain.Cliente;
import com.cursomc.dto.ClienteDTO;
import com.cursomc.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService service;

	/**
	 * Lista os objetos
	 * 
	 * @return
	 */
	@GetMapping
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<Cliente> list = service.findAll();
		List<ClienteDTO> listDto = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}

	/**
	 * Lista com paginação
	 * 
	 * @param page
	 * @param linesPerPage
	 * @param orderBy
	 * @param direction
	 * @return
	 */
	// categorias/page?page=0&linesPerPage=20 ....
	@GetMapping(value = "/page")
	public ResponseEntity<Page<ClienteDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {

		Page<Cliente> list = service.findPage(page, linesPerPage, orderBy, direction);
		/*
		 * lista de ClienteDTO, stream()= varre uma lista; map= efetua uma operação
		 * para cada elemento da lista; collect(Collectors.toList()) transforma em na
		 * lista nova do objeto novo
		 */
		Page<ClienteDTO> listDto = list.map(obj -> new ClienteDTO(obj));
		return ResponseEntity.ok().body(listDto);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Long id) {
		System.out.println(">>>>> Rest busca uma Cliente por id");
		Cliente obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}

	/**
	 * Cria um Objeto void corpo vazio, @RequestBody já converte o json recebido em
	 * obj categoria
	 * 
	 * @param obj
	 * @return
	 */
	@PostMapping
//	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteDTO objDto) {
//		Cliente obj = ClienteDTO.toCliente(objDto);
//		obj = service.insert(obj);

		// devolvendo a uri do objeto criado
//		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
//		return ResponseEntity.created(uri).build();
//	}

	/**
	 * Atualiza um objeto
	 * 
	 * @param obj
	 * @param id
	 * @return
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@RequestBody Cliente obj, @PathVariable Long id) {
		obj.setId(id); // pra garantir que o id é o mesmo recebido
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Deleta um objeto
	 * 
	 * @param obj
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
