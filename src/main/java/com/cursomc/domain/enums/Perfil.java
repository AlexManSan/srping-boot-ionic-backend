package com.cursomc.domain.enums;

public enum Perfil {

	ADMIN(1, "ROLE_ADMIN"),
	CLIENTE(2, "ROLE_CLIENTE");
	
	private int cod;
	private String descricao;
	
	/**
	 * Construtor
	 * @param cod
	 * @param descricao
	 */
	private Perfil(int cod, String descricao) {
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
	public static Perfil toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}else {
			for(Perfil x : Perfil.values()) {
				if(cod.equals(x.getCod())) {
					return x;
				}
			}
		}
		
		throw new IllegalArgumentException("id inválido: "+ cod);
	}
	
	
}
