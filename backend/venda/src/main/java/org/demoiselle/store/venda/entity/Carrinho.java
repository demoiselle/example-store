package org.demoiselle.store.venda.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Guarda as informações dos itens armazenados no carrinho.
 * 
 */
public class Carrinho implements Serializable {

	private static final long serialVersionUID = -3678488610454757181L;
	protected List<ItemCarrinho> itens;	
	
	public Carrinho(){
		 itens = new ArrayList<ItemCarrinho>();		
	}
	
	public void adicionarItem(ItemCarrinho item){
		itens.add(item);
	}
	
	public void removerItem(ItemCarrinho item){
		itens.remove(item);
	}
	
	public boolean contem(Long codigoProduto){	
		for(ItemCarrinho item:itens){
			if (item.codigoProduto == codigoProduto) 
				return true;
		}
		return false;
	}
	
	public List<ItemCarrinho> getItens() {
		return itens;
	}
	
	public BigDecimal getValorTotal(){
		BigDecimal total = BigDecimal.valueOf(0.0);
		for(ItemCarrinho item:itens){
			total = total.add(item.getValorComDesconto());
		}
		return total;
	}	
}