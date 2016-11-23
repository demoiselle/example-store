/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.demoiselle.jee7.example.store.sale.service;

import java.util.List;

import javax.inject.Inject;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import javax.ws.rs.core.MediaType;

import org.demoiselle.jee7.example.store.sale.bussines.RulesBC;
import org.demoiselle.jee7.example.store.sale.entity.Rules;

import io.swagger.annotations.Api;
import javax.transaction.Transactional;

/**
 * Process the sales.
 * 
 */
@Api("Rules")
@Path("rules")
@Consumes({MediaType.APPLICATION_JSON})
public class RulesREST {
  
    @Inject
    private RulesBC bc;

 
    @GET
    @Path("{id}")
    @Transactional
    public Rules get(@PathParam("id") final Long id) {
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
    public List<Rules> findAll() {
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
    public void insert(final Rules bean) {
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
    public void update(final Rules bean) {
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
}


