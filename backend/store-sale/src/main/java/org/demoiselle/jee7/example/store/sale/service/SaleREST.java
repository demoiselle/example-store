/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.demoiselle.jee7.example.store.sale.service;

import java.util.List;

import javax.inject.Inject;
import javax.script.ScriptException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.demoiselle.jee.persistence.crud.AbstractREST;
import org.demoiselle.jee.security.annotation.LoggedIn;
import org.demoiselle.jee7.example.store.sale.bussines.SaleBC;
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
@Consumes({MediaType.APPLICATION_JSON})
public class SaleREST {
  
    @Inject
    private SaleBC bc;
    
    
    @GET
    @LoggedIn
    @Path("{id}")
    @Transactional
    public Sale get(@PathParam("id") final Long id) {
        return bc.find(id);
    }
    /**
     * Removes a instance.
     *
     * @param id Entity with the given identifier
     */
    @DELETE
    @Path("{id}")
    @Transactional
    public void delete(@PathParam("id") final Long id) {
        bc.remove((bc.find(id)));
    }

    /**
     * Gets the results.
     *
     * @return Ruleshe list of matched query results.
     */
    @GET
    @Transactional
    public List<Sale> findAll() {
        return bc.findAll();
    }

    /**
     * Delegates the insert operation of the given instance.
     *
     * @param bean A entity to be inserted by the delegate
     * @return
     */
    @POST
    @Transactional
    public void insert(final Sale bean) {
        bc.create(bean);
    }

    
    /**
     * Delegates the update operation of the given instance.
     *
     * @param bean Ruleshe instance containing the updated state.
     * @return 
     * @return
     */
    @PUT
    @Transactional
    public void update(final Sale bean) {
        bc.edit(bean);
    }

    /**
     *
     * @return
     */
    @GET
    @Path("count")
    @Transactional
    public int count() {
        return bc.count();
    }
    

    @POST
    @Path("salePreview")
    @Produces({MediaType.APPLICATION_JSON})
	@Transactional
	public Cart salePreview (String objeto) throws ScriptException {    	
    	Gson gson = new Gson();
    	Cart cart = gson.fromJson(objeto, Cart.class);   
  
    	return bc.salePreview(cart);
    }
    
    @POST
    @Path("saleComplete")
    @Produces({MediaType.APPLICATION_JSON})
	@Transactional
    public Cart saleComplete (String objeto) throws ScriptException {   	
	  Gson gson = new Gson();
      Cart cart = gson.fromJson(objeto, Cart.class);   
      
      return bc.saleComplete(cart);
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