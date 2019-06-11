package com.cursomc.domain.enums;

public enum EstadoPagamento {

	PENDENTE(1, "Pendente"),
	QUITADO(2, "Quitado"),
	CANCELADO(3, "Cancelado");
	
	private int cod;
	private String descricao;
	
	/**
	 * Construtor
	 * @param cod
	 * @param descricao
	 */
	private EstadoPagamento(int cod, String descricao) {
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
	public static EstadoPagamento toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}else {
			for(EstadoPagamento x : EstadoPagamento.values()) {
				if(cod.equals(x.getCod())) {
					return x;
				}
			}
		}
		
		throw new IllegalArgumentException("id inválido: "+ cod);
	}
	
	
}
