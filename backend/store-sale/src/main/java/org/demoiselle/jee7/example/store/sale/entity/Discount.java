package org.demoiselle.jee7.example.store.sale.entity;

import java.math.BigDecimal;

public class Discount {

	protected String identificador;
	protected BigDecimal percentual;
	protected Boolean acumulativo = false;

	public Discount(String identificador) {
		this.identificador = identificador;
	}

	public Discount(String identificador, BigDecimal percentual) {
		this(identificador);
		this.percentual = percentual;
	}

	public Discount(String identificador, BigDecimal percentual, Boolean acumulativo) {
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
