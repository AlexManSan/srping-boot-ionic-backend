package com.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.cursomc.domain.Cliente;

public class ClienteDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotEmpty(message="O campo 'NOME' é obrigatório.")
	@Length(min=5, max=120, message="O tamanho deve ser entre 5 e 120 caracteres.")
	private String nome;
	
	@Email(message="'EMAIL' inválido.")
	@NotEmpty(message="O campo 'EMAIL' é obrigatório.")
	private String email;
	
	/**
	 * Construtor Vazio
	 */
	public ClienteDTO() {}
	
	/**
	 * Construtor que recebe um obj e transforma em objDto
	 * @param obj
	 */
	public ClienteDTO(Cliente obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.email = obj.getEmail();
	}

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
