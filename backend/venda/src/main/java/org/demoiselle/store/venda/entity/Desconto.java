package org.demoiselle.store.venda.entity;

import java.math.BigDecimal;

public class Desconto {

	protected String identificador;
	protected BigDecimal percentual;
	protected Boolean acumulativo = false;

	public Desconto(String identificador) {
		this.identificador = identificador;
	}

	public Desconto(String identificador, BigDecimal percentual) {
		this(identificador);
		this.percentual = percentual;
	}

	public Desconto(String identificador, BigDecimal percentual, Boolean acumulativo) {
		this(identificador ,percentual);
		this.acumulativo = acumulativo;
	}
	
	public String getIdentificador() {
		return identificador;
	}
	
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	
	public Boolean getAcumulativo() {
		return acumulativo;
	}

	public BigDecimal getPercentual() {
		return percentual;
	}

	public void setAcumulativo(Boolean acumulativo) {
		this.acumulativo = acumulativo;
	}

	public void setPercentual(BigDecimal percentual) {
		this.percentual = percentual;
	}	
}
