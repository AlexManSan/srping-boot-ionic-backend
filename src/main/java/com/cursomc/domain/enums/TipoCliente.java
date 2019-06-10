package com.cursomc.domain.enums;

public enum TipoCliente {

	PESSOAFISICA(1, "Pessoa Física"),
	PESSOAJURIDICA(2, "Pessoa Jurídica");
	
	private int cod;
	private String descricao;
	
	/**
	 * Construtor
	 * @param cod
	 * @param descricao
	 */
	private TipoCliente(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}
	
	/**
	 * Estatico porque tem que ser possível ser executada sem precisar instaciar objeto
	 * @param cod
	 * @return
	 */
	public static TipoCliente toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}else {
			for(TipoCliente x : TipoCliente.values()) {
				if(cod.equals(x.getCod())) {
					return x;
				}
			}
		}
		
		throw new IllegalArgumentException("id inválido: "+ cod);
	}
	
	
}
