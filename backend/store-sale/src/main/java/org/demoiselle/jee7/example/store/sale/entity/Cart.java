package org.demoiselle.jee7.example.store.sale.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Cart
 * 
 */
public class Cart implements Serializable {

	private static final long serialVersionUID = -3678488610454757181L;
	protected List<ItemCart> itens;	
	
	private Long idSale;
	
    private List<String> listaCupons;
	
	public Cart(){
		 itens = new ArrayList<ItemCart>();		
		 listaCupons = new ArrayList<String>();
	}
		
	public Long getIdSale() {
		return idSale;
	}

	public void setIdSale(Long idSale) {
		this.idSale = idSale;
	}

	public List<String> getListaCupons() {
		return listaCupons;
	}
	
	public void setListaCupons(List<String> listaCupons) {
		this.listaCupons = listaCupons;
	}

	public void adicionarItem(ItemCart item){
		itens.add(item);
	}
	
	public void removerItem(ItemCart item){
		itens.remove(item);
	}
	
	public boolean contem(Long codigoProduto){	
		for(ItemCart item:itens){
			if (item.codigoProduto == codigoProduto) 
				return true;
		}
		return false;
	}
	
	public List<ItemCart> getItens() {
		return itens;
	}
	
	public BigDecimal getValorTotal(){
		BigDecimal total = BigDecimal.valueOf(0.0);
		for(ItemCart item:itens){
			total = total.add(item.getValorComDesconto());
		}
		return total;
	}	
}