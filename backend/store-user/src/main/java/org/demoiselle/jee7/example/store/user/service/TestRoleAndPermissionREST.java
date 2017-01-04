package org.demoiselle.jee7.example.store.user.service;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.demoiselle.jee.core.api.security.DemoiselleUser;
import org.demoiselle.jee.security.annotation.Authenticated;
import org.demoiselle.jee.security.annotation.RequiredPermission;
import org.demoiselle.jee.security.annotation.RequiredRole;
import org.jose4j.json.internal.json_simple.JSONObject;

import io.swagger.annotations.Api;

@Path("testRoleAndPermission")
@Api("Role and Permission Test")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class TestRoleAndPermissionREST {

	@Inject
	private DemoiselleUser loggedUser;

	@SuppressWarnings("unchecked")
	public JSONObject loggedUserObject() {
		JSONObject json = new JSONObject();
		json.put("identity", loggedUser.getIdentity());
		json.put("name", loggedUser.getName());
		json.put("roles", loggedUser.getRoles());
		json.put("permissions", loggedUser.getPermissions());
		json.put("tenant", loggedUser.getParams("Tenant"));
		return json;
	}

	@GET
	@Path("sem")
	@SuppressWarnings("unchecked")
	public Response testeSem() {
		JSONObject json = new JSONObject();
		json.put("message", "Foi sem");
		return Response.ok(json).build();
	}

	@GET
	@Path("com")
	@Authenticated
	public Response testeCom() {
		return Response.ok(loggedUserObject()).build();
	}

	@GET
	@Path("role/administrator")
	@RequiredRole("ADMINISTRATOR")
	public Response testeRoleOK() {
		return Response.ok(loggedUserObject()).build();
	}

	@GET
	@Path("role/usuario")
	@RequiredRole("USUARIO")
	public Response testeRoleErro() {
		return Response.ok(loggedUserObject()).build();
	}

	@GET
	@Path("permission/categoria/consultar")
	@RequiredPermission(resource = "Categoria", operation = "Consultar")
	public Response testePermissionOK() {
		return Response.ok(loggedUserObject()).build();
	}

	@GET
	@Path("permission/produto/incluir")
	@RequiredPermission(resource = "Produto", operation = "Incluir")
	public Response testePermissionErro() {
		return Response.ok(loggedUserObject()).build();
	}

}
