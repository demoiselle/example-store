/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.demoiselle.store.usuario.service.multitenancy;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Response;

import org.demoiselle.jee.core.api.security.SecurityContext;
import org.demoiselle.jee.rest.annotation.CacheControl;
import org.demoiselle.jee.security.annotation.Cors;
import org.demoiselle.jee.security.annotation.LoggedIn;
import org.demoiselle.jee.security.annotation.RequiredPermission;
import org.demoiselle.jee.security.annotation.RequiredRole;
import org.demoiselle.store.usuario.business.TenancyBC;

import io.swagger.annotations.Api;
import io.swagger.jaxrs.PATCH;

/**
 *
 * @author 70744416353
 */
@Api("Teste")
@Path("test")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class TesteREST {

	@Inject
	private SecurityContext securityContext;

	@Inject
	private TenancyBC business;

	// @Inject
	// private DemoisellePrincipal loggedUser;
	//
	// @Inject
	// private Token token;
	//
	// @Inject
	// private DemoiselleSecurityMessages bundle;

	@GET
	@LoggedIn
	@Path("cache")
	@CacheControl("max-age=600")
	public void testeCache(@Suspended final AsyncResponse asyncResponse) {
		asyncResponse.resume(doCache());
	}

	private Response doCache() {
		return Response.ok("{\"msg\":\"cache ok\"}").build();
	}

	@GET
	@Path("sem")
	public Response testeSem() {
		return Response.ok().entity("{\"msg\":\"Foi sem\"}").build();
	}

	@GET
	@Path("com")
	@LoggedIn
	public Response testeCom() {
		return Response.ok().entity(securityContext.getUser().toString()).build();
	}

	@GET
	@Path("role/ok")
	@RequiredRole("ADMINISTRATOR")
	public Response testeRoleOK() {
		return Response.ok().entity(securityContext.getUser().toString()).build();
	}

	@GET
	@Path("role/error")
	@RequiredRole("USUARIO")
	public Response testeRoleErro() {
		return Response.ok().entity(securityContext.getUser().toString()).build();
	}

	@GET
	@Path("permission/ok")
	@RequiredPermission(resource = "Categoria", operation = "Consultar")
	public Response testePermissionOK() {
		return Response.ok().entity(securityContext.getUser().toString()).build();
	}

	@GET
	@Path("permission/error")
	@RequiredPermission(resource = "Produto", operation = "Incluir")
	public Response testePermissionErro() {
		return Response.ok().entity(securityContext.getUser().toString()).build();
	}

	@GET
	@Cors
	@CacheControl("max-age=600")
	public Response testeGet() {
		return Response.ok().entity(business.listAllTenants()).build();
	}

	@Cors
	@POST
	public Response testePost() {
		return Response.ok("{\"msg\":\"post ok\"}").build();
	}

	@PUT
	@Cors
	public Response testePut() {
		return Response.ok("{\"msg\":\"put ok\"}").build();
	}

	@Cors
	@PATCH
	public Response testePatch() {
		return Response.ok("{\"msg\":\"patch ok\"}").build();
	}

	@Cors
	@DELETE
	public Response testeDelete() {
		return Response.ok("{\"msg\":\"delete ok\"}").build();
	}

}
