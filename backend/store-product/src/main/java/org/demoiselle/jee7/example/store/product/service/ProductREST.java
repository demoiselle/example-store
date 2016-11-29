/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.demoiselle.jee7.example.store.product.service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.websocket.server.PathParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import org.demoiselle.jee.core.api.security.SecurityContext;
import org.demoiselle.jee.persistence.crud.AbstractREST;
import org.demoiselle.jee.rest.annotation.ValidatePayload;
import org.demoiselle.jee.security.annotation.LoggedIn;
import org.demoiselle.jee7.example.store.product.entity.Product;

/**
 *
 */
@Api("Produto")
@Path("product")
public class ProductREST extends AbstractREST<Product, Long > {

    @Inject
    private SecurityContext securityContext;
       
    @Override
    @POST
    @Transactional
    @ValidatePayload
    @ApiOperation(value = "Insere entidade no banco")
    @LoggedIn    
    public Product persist(Product entity) {
        return bc.persist(entity);
    }
    
    @Override   
    @PUT
    @Transactional
    @ValidatePayload
    @LoggedIn    
    @ApiOperation(value = "Atualiza a entidade", notes = "Atualiza")
    public Product merge(Product entity) {
        return bc.merge(entity);
    }
    
    @Override   
    @DELETE
    @Path("{id}")
    @Transactional
    @LoggedIn    
    @ApiOperation(value = "Remove entidade")
    public void remove(@PathParam("id") Long id) {
        bc.remove(id);
    }
    
}