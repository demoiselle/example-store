package org.demoiselle.store.venda.test;

import static org.junit.Assert.assertTrue;

import java.awt.Component;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.demoiselle.store.venda.entity.CalculadoraDesconto;
import org.demoiselle.store.venda.entity.Desconto;
import org.junit.Before;
import org.junit.Test;


public class CalculadoraDescontoTest {
	
	private  CalculadoraDesconto calculadora;
	private  List<Desconto> listaDescontos;	
	private  BigDecimal percentualDesconto1;
	private  BigDecimal percentualDesconto2;
	 
	@Before public void setUp() {
		listaDescontos = new ArrayList<Desconto>();
		percentualDesconto1        = BigDecimal.valueOf(5);
		percentualDesconto2 	    = BigDecimal.valueOf(10);
		
	}
	
	@Test
	public void calculoDescontoNaoAcumulativoTest() {
		
		BigDecimal percentualDescontoEsperado = percentualDesconto2;
		
		Desconto desc1 = new Desconto("desc1", percentualDesconto1);
		Desconto desc2 = new Desconto("desc2", percentualDesconto2);
		
		listaDescontos.add(desc1);
		listaDescontos.add(desc2);
		
		assertTrue(percentualDescontoEsperado.equals(calculadora.consolidarDescontos(listaDescontos)));
	}
	
	@Test
	public void calculoDescontoAcumulativoTest() {
		listaDescontos = new ArrayList<Desconto>();
		BigDecimal percentualDesconto1        = BigDecimal.valueOf(5);
		BigDecimal percentualDesconto2 	      = BigDecimal.valueOf(10);
		BigDecimal percentualDescontoEsperado = percentualDesconto1.add(percentualDesconto2);
		
		Desconto desc1 = new Desconto("desc1", percentualDesconto1, true);
		Desconto desc2 = new Desconto("desc2", percentualDesconto2, true);
		
		listaDescontos.add(desc1);
		listaDescontos.add(desc2);
		
		assertTrue(percentualDescontoEsperado.equals(calculadora.consolidarDescontos(listaDescontos)));
	}	
	
	
}