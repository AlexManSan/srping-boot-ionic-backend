package com.cursomc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.cursomc.domain.enums.Perfil;
import com.cursomc.domain.enums.TipoCliente;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Cliente implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	@Column(unique=true)
	private String email;
	private String cpfOuCnpj;
	//modificado para integer
	private Integer tipo;
	
	@JsonIgnore
	private String senha;
	
	//cascade reflete as operações nos endereços também 
	@OneToMany(mappedBy="cliente", cascade=CascadeType.ALL)
	private List<Endereco> enderecos = new ArrayList<>();
	
	/**
	 * Conjunto de strings que não aceita repetição
	 */
	@ElementCollection
	@CollectionTable(name ="TELEFONE") // nome da tabela
	private Set<String> telefones = new HashSet<>();
	
	@ElementCollection(fetch=FetchType.EAGER) // carrega junto ao carregar um cliente
	@CollectionTable(name ="PERFIS") // nome da tabela
	private Set<Integer> perfis = new HashSet<>();
	
	@JsonIgnore
	@OneToMany(mappedBy="cliente")
	private List<Pedido> pedidos = new ArrayList<>();
	
	/**
	 * Construtor Vazio
	 */
	public Cliente() { addPerfil(Perfil.CLIENTE); }

	/**
	 * Construtor completo sem as listas
	 * @param id
	 * @param nome
	 * @param email
	 * @param cpfOuCnpj
	 * @param tipo
	 */
	public Cliente(Long id, String nome, String email, String cpfOuCnpj, TipoCliente tipo, String senha) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.cpfOuCnpj = cpfOuCnpj;
		this.tipo = (tipo==null) ? null : tipo.getCod();
		this.senha = senha;
		addPerfil(Perfil.CLIENTE);
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

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}

	//modificado para atender o tipo integer
	public TipoCliente getTipo() {
		return TipoCliente.toEnum(tipo);
	}

	// modificado para atender o tipo integer
	public void setTipo(TipoCliente tipo) {
		this.tipo = tipo.getCod();
	}
	
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public Set<String> getTelefones() {
		return telefones;
	}

	public void setTelefones(Set<String> telefones) {
		this.telefones = telefones;
	}
	
	public Set<Perfil> getPerfis() {
		return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
	}
	
	public void addPerfil(Perfil perfil) {
		perfis.add(perfil.getCod());
	}
	
	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
}
