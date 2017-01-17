package org.demoiselle.jee7.example.store.sale.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *  Cart item. 
 */
public class ItemCart implements Serializable {
	private static final long serialVersionUID = -7730398389647465451L;
	protected Long codigoProduto;
	protected String nomeProduto;
	protected Integer quantidade;
	protected BigDecimal valor;
	protected List<Discount> descontos;

	public ItemCart(Long codigoProduto){
		this.codigoProduto = codigoProduto;
	}
	
	public ItemCart(Long codigoProduto, String nomeProduto, BigDecimal valor) {
		this.codigoProduto = codigoProduto;
		this.nomeProduto = nomeProduto;
		this.valor = valor;
		this.descontos = new ArrayList<Discount>();
	}

	public Long getCodigoProduto() {
		return codigoProduto;
	}

	public void setCodigoProduto(Long codigoProduto) {
		this.codigoProduto = codigoProduto;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getValorComDesconto() {
		BigDecimal resultado = getValor().multiply(BigDecimal.valueOf(1).subtract(getPercentualDesconto().divide(BigDecimal.valueOf(100))));
		return BigDecimal.valueOf(getQuantidade().longValue()).multiply(resultado);
	}

	public BigDecimal getPercentualDesconto() {
		return DiscountCalculator.consolidarDescontos(this.descontos);
	}	

	public void addDesconto(String identificador, BigDecimal percentual, Boolean acumulativo) {
		Discount desc = new Discount ( identificador,percentual,acumulativo);
		if(this.descontos== null)
			this.descontos= new  ArrayList<Discount>();
		
		for(Discount d : this.descontos){
			if(d.identificador.equals(identificador)){
				return;			
			}
		}
		
		this.descontos.add(desc);
	}
	
	public void registrarDesconto(Discount desconto) {		
		this.descontos.add(desconto);
	}
	
	public List<Discount> getDescontos() {
		return this.descontos;
	}
}
