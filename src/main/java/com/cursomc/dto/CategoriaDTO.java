package com.cursomc.dto;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.cursomc.domain.Categoria;

public class CategoriaDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotEmpty(message="O campo 'NOME' é obrigatório.")
	@Length(min=5, max=80, message="O tamanho deve ser entre 5 e 80 caracteres.")
	private String nome;
	
	/**
	 * Construtor vazio
	 */
	public CategoriaDTO() {	}

	public CategoriaDTO(Categoria obj) {
		id = obj.getId();
		nome = obj.getNome();
	}
	
	/*get e set*/
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	/**
	 * Retorna uma Categoria de uma CategoriaDTO
	 * @param objDto
	 * @return
	 */
	public static Categoria toCategoria(CategoriaDTO objDto) {
		return new Categoria(objDto.getId(), objDto.getNome()); 
	}
	
	/**
	 * Recebe um lista de categoria e retorna uma lista de CategoriaDTO
	 * @param lista
	 * @return
	 */
	public static List<CategoriaDTO> toCategoriaDTO(List<Categoria> lista) {
		/* lista de CategoriaDTO, stream()= varre uma lista; map= efetua uma operação para cada elemento da lista; 
		 * collect(Collectors.toList()) transforma em na lista nova do objeto novo */
		List<CategoriaDTO> listDto = lista.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
		return listDto;
	}
	
	
}
