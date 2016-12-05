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
import org.demoiselle.jee.security.annotation.RequiredRole;
import org.demoiselle.jee7.example.store.product.entity.Category;

@Api("Category")
@Path("category")
public class CategoryREST extends AbstractREST<Category, Long > {

    @SuppressWarnings("unused")
	@Inject
    private SecurityContext securityContext;
       
    @Override
    @POST
    @Transactional
    @ValidatePayload
    @ApiOperation(value = "Insere entidade no banco")
    @RequiredRole("ADMIN")
    public Category persist(Category entity) {
    	entity.setId(null);
        return bc.persist(entity);
    }
    
    @Override   
    @PUT
    @Transactional
    @ValidatePayload
    @RequiredRole("ADMIN")
    @ApiOperation(value = "Atualiza a entidade", notes = "Atualiza")
    public Category merge(Category entity) {
        return bc.merge(entity);
    }
    
    @Override   
    @DELETE
    @Path("{id}")
    @Transactional
    @RequiredRole("ADMIN")
    @ApiOperation(value = "Remove entidade")
    public void remove(@PathParam("id") Long id) {
        bc.remove(id);
    }
    
}