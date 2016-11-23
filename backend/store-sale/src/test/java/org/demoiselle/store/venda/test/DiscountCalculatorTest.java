package org.demoiselle.store.venda.test;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.demoiselle.jee7.example.store.sale.entity.Discount;
import org.demoiselle.jee7.example.store.sale.entity.DiscountCalculator;
import org.junit.Before;
import org.junit.Test;

public class DiscountCalculatorTest {

	private DiscountCalculator calculadora;
	private List<Discount> listaDescontos;
	private BigDecimal percentualDesconto1;
	private BigDecimal percentualDesconto2;

	@Before
	public void setUp() {
		listaDescontos = new ArrayList<Discount>();
		percentualDesconto1 = BigDecimal.valueOf(5);
		percentualDesconto2 = BigDecimal.valueOf(10);

	}

	@SuppressWarnings("static-access")
	@Test
	public void calculoDescontoNaoAcumulativoTest() {

		BigDecimal percentualDescontoEsperado = percentualDesconto2;

		Discount desc1 = new Discount("desc1", percentualDesconto1);
		Discount desc2 = new Discount("desc2", percentualDesconto2);

		listaDescontos.add(desc1);
		listaDescontos.add(desc2);

		assertTrue(percentualDescontoEsperado.equals(calculadora.consolidarDescontos(listaDescontos)));
	}

	@SuppressWarnings("static-access")
	@Test
	public void calculoDescontoAcumulativoTest() {
		listaDescontos = new ArrayList<Discount>();
		BigDecimal percentualDesconto1 = BigDecimal.valueOf(5);
		BigDecimal percentualDesconto2 = BigDecimal.valueOf(10);
		BigDecimal percentualDescontoEsperado = percentualDesconto1.add(percentualDesconto2);

		Discount desc1 = new Discount("desc1", percentualDesconto1, true);
		Discount desc2 = new Discount("desc2", percentualDesconto2, true);

		listaDescontos.add(desc1);
		listaDescontos.add(desc2);

		assertTrue(percentualDescontoEsperado.equals(calculadora.consolidarDescontos(listaDescontos)));
	}

}