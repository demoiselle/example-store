package org.demoiselle.jee7.example.store.product.service;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.demoiselle.jee.persistence.crud.AbstractREST;
import org.demoiselle.jee7.example.store.product.entity.Category;

import io.swagger.annotations.Api;

@Api("Categories")
@Path("categories")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
@RequestScoped
public class CategoryREST extends AbstractREST<Category, Long> {

	/*@Override
	@POST
	@Transactional
	@ValidatePayload
	@RequiredRole("ADMINISTRATOR")
	public Category persist(Category entity) {
		return super.persist(entity);
	}

	@Override
	@PUT
	@Transactional
	@ValidatePayload
	@RequiredRole("ADMINISTRATOR")
	public Category merge(Category entity) {
		return super.merge(entity);
	}

	@Override
	@DELETE
	@Path("{id}")
	@Transactional
	@RequiredRole("ADMINISTRATOR")
	public void remove(@PathParam("id") Long id) {
		super.remove(id);
	}*/
	
	/*@Override
    @DELETE
    @Path("{id}")
    @Transactional
    @ApiOperation(value = "Remove entidade")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
    public void remove(@PathParam("id") final Long id) {
        super.remove(id);
    }*/

}