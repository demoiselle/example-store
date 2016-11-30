package org.demoiselle.jee7.example.store.sale.entity;

import java.math.BigDecimal;
import java.util.List;

/**
 * Calcula o percentual de desconto Total baseado na lista de descontos de entrada.
 * 
 * 
 */
public class DiscountCalculator {

	public static BigDecimal consolidarDescontos( List<Discount> listaDescontos) {		
		BigDecimal percentual = BigDecimal.valueOf(0.0);
		BigDecimal maior = BigDecimal.valueOf(0.0);
		
		if(listaDescontos != null){
			for(Discount desconto: listaDescontos){
				if(desconto.getAcumulativo()){
					percentual = percentual.add(desconto.getPercentual());				
				}else{
					if(desconto.getPercentual().compareTo(maior) > 0){
						maior = desconto.getPercentual();					
					}
				}
			}
			percentual = percentual.add(maior);
			if(percentual.compareTo(BigDecimal.valueOf(100)) > 0){
				percentual = BigDecimal.valueOf(100);
			}
		}
		return percentual;
	}
}