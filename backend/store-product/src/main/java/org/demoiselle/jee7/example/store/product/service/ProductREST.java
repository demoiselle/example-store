package org.demoiselle.jee7.example.store.product.service;

import javax.transaction.Transactional;
import javax.websocket.server.PathParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

import org.demoiselle.jee.persistence.crud.AbstractREST;
import org.demoiselle.jee.rest.annotation.ValidatePayload;
import org.demoiselle.jee.security.annotation.LoggedIn;
import org.demoiselle.jee.security.annotation.RequiredRole;
import org.demoiselle.jee7.example.store.product.entity.Product;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("Product")
@Path("product")
public class ProductREST extends AbstractREST<Product, Long> {

	@Override
	@POST
	@Transactional
	@ValidatePayload
	@ApiOperation(value = "Insere entidade no banco")
	@RequiredRole("ADMINISTRATOR")
	public Product persist(Product entity) {
		entity.setId(null);
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
	@RequiredRole("ADMINISTRATOR")
	@ApiOperation(value = "Remove entidade")
	public void remove(@PathParam("id") Long id) {
		bc.remove(id);
	}

}