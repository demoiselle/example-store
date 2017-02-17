/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.demoiselle.jee7.example.store.sale.service;

import java.util.List;

import javax.inject.Inject;
import javax.script.ScriptException;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.demoiselle.jee.core.api.crud.Result;
import org.demoiselle.jee.core.api.security.SecurityContext;
import org.demoiselle.jee.crud.AbstractREST;
import org.demoiselle.jee.security.annotation.Authenticated;
import org.demoiselle.jee.security.annotation.RequiredRole;
import org.demoiselle.jee7.example.store.sale.business.SaleBC;
import org.demoiselle.jee7.example.store.sale.entity.Cart;
import org.demoiselle.jee7.example.store.sale.entity.Itens;
import org.demoiselle.jee7.example.store.sale.entity.Sale;

import com.google.gson.Gson;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Process the sales.
 * 
 */
@Api("Sales")
@Path("sales")
public class SaleREST extends AbstractREST<Sale, Long> {
	
	@Inject
	private SecurityContext securityContext;

	@POST
	@Path("salePreview")
	@Transactional
	public Cart salePreview(String objeto) throws ScriptException {
		Gson gson = new Gson();
		Cart cart = gson.fromJson(objeto, Cart.class);

		return ((SaleBC) bc).salePreview(cart);
	}

	@POST
	@Path("saleComplete")
	@Transactional
	public Cart saleComplete(String objeto) throws ScriptException {
		Gson gson = new Gson();
		Cart cart = gson.fromJson(objeto, Cart.class);

		return ((SaleBC) bc).saleComplete(cart);
	}

	@Authenticated
	@GET
	@Path("listSaleItens/{id}")
	@Transactional
	@ApiOperation(value = "Busca por ID")
	public List<Itens> listSaleItens(@PathParam("id") final Long id) {
		return ((SaleBC) bc).listSaleItens(id);
	}

	@Authenticated
	@GET
	@Path("listUserSales")
	@Transactional
	public List<Sale> listUserSales() {
		return ((SaleBC) bc).listUserSales(securityContext.getUser().getName());
	}
	
	@Override
	@GET	
	@Transactional
	@RequiredRole("ADMINISTRATOR")
	public Result find() {
		return bc.find();
	}
	
	/*
	 * Cart Sample ...
	 * 
	 * { "itens": [ { "codigoProduto": 1, "quantidade": 1, "valor": 100 }, {
	 * "codigoProduto": 2, "quantidade": 1, "valor": 100 }, { "codigoProduto":
	 * 3, "quantidade": 1, "valor": 100 }, { "codigoProduto": 4, "quantidade":
	 * 1, "valor": 100 } ], "listaCupons": ["desconto10"] }
	 */
}