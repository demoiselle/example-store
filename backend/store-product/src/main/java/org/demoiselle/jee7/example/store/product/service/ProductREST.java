package org.demoiselle.jee7.example.store.product.service;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.demoiselle.jee.crud.AbstractBusiness;
import org.demoiselle.jee.crud.AbstractREST;
import org.demoiselle.jee.security.annotation.Authenticated;
import org.demoiselle.jee.security.annotation.RequiredRole;
import org.demoiselle.jee7.example.store.product.entity.Product;

@Path("products")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
@RequestScoped
public class ProductREST extends AbstractREST<Product, Long> {

	@Context
	protected UriInfo uriInfo;

	@Inject
	protected AbstractBusiness<Product, Long> bc;

	@Override
	@POST
	@Transactional
	@RequiredRole("ADMINISTRATOR")
	public Product persist(@Valid Product entity) {
		entity.setId(null);
		return bc.persist(entity);
	}

	@Override
	@PUT
	@Transactional
	@RequiredRole("ADMINISTRATOR")
	public Product merge(@Valid Product entity) {
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