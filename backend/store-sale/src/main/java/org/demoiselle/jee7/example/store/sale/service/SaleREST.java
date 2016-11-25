/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.demoiselle.jee7.example.store.sale.service;


import javax.inject.Inject;
import javax.script.ScriptException;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.demoiselle.jee.persistence.crud.AbstractREST;
import org.demoiselle.jee7.example.store.sale.business.SaleBC;
import org.demoiselle.jee7.example.store.sale.entity.Cart;
import org.demoiselle.jee7.example.store.sale.entity.Sale;

import com.google.gson.Gson;

import io.swagger.annotations.Api;
import javax.transaction.Transactional;

/**
 * Process the sales.
 * 
 */
@Api("Sales")
@Path("sale")

public class SaleREST extends AbstractREST<Sale,Long>{
  
	@Inject 
	private SaleBC saleBC;
		       	
    @POST
    @Path("salePreview")   
	@Transactional
	public Cart salePreview (String objeto) throws ScriptException {    	
    	Gson gson = new Gson();
    	Cart cart = gson.fromJson(objeto, Cart.class);   
  
    	return saleBC.salePreview(cart);
    }
    
    @POST
    @Path("saleComplete")
	@Transactional
    public Cart saleComplete (String objeto) throws ScriptException {   	
	  Gson gson = new Gson();
      Cart cart = gson.fromJson(objeto, Cart.class);   
      
      return saleBC.saleComplete(cart);     
    }

    /* Cart Sample ...
     
     {
	  "itens": [
	    {
	      "codigoProduto": 1,
	      "quantidade": 1,
	      "valor": 100
	    },
	    {
	      "codigoProduto": 2,
	      "quantidade": 1,
	      "valor": 100
	    },
	    {
	      "codigoProduto": 3,
	      "quantidade": 1,
	      "valor": 100
	    },
	    {
	      "codigoProduto": 4,
	      "quantidade": 1,
	      "valor": 100
	    }
	  ],
          "listaCupons": ["desconto10"]
	}          
     */    
}