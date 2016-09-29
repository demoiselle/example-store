package org.demoiselle.jee7.entity;

import java.math.BigDecimal;
import java.util.List;

//cassiomaes
public class CalculadoraDesconto {

	public static BigDecimal consolidarDescontos( List<Desconto> listaDescontos) {
		
		BigDecimal percentual = BigDecimal.valueOf(0.0);
		BigDecimal maior = BigDecimal.valueOf(0.0);
		
		if(listaDescontos != null){
			for(Desconto desconto: listaDescontos){
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
