package org.demoiselle.jee7.example.store.product.service;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.demoiselle.jee.crud.AbstractREST;
import org.demoiselle.jee.security.annotation.RequiredRole;
import org.demoiselle.jee7.example.store.product.entity.Category;

@Path("categories")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
@RequestScoped
public class CategoryREST extends AbstractREST<Category, Long> {

	@Override
	@POST
	@Transactional
	@RequiredRole("ADMINISTRATOR")
	public Category persist(@Valid Category entity) {
		entity.setId(null);
		return bc.persist(entity);
	}

	@Override
	@PUT
	@Transactional
	@RequiredRole("ADMINISTRATOR")
	public Category merge(@Valid Category entity) {
		return bc.merge(entity);
	}

	@Override
	@DELETE
	@Path("{id}")
	@Transactional
	@RequiredRole("ADMINISTRATOR")
	public void remove(@PathParam("id") Long id) {
		bc.remove(id);
	}

}