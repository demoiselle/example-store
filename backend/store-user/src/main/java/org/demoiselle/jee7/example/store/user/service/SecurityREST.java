package org.demoiselle.jee7.example.store.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.demoiselle.jee.core.api.security.DemoisellePrincipal;
import org.demoiselle.jee.core.api.security.SecurityContext;
import org.demoiselle.jee.core.api.security.Token;
import org.demoiselle.jee.multitenancy.hibernate.context.MultiTenantContext;
import org.demoiselle.jee.security.annotation.LoggedIn;
import org.demoiselle.jee.security.annotation.RequiredPermission;
import org.demoiselle.jee.security.annotation.RequiredRole;
import org.demoiselle.jee.security.exception.DemoiselleSecurityException;
import org.demoiselle.jee.security.message.DemoiselleSecurityMessages;
import org.demoiselle.jee7.example.store.user.business.UserBC;
import org.demoiselle.jee7.example.store.user.entity.User;
import org.demoiselle.jee7.example.store.user.security.Credentials;
import org.jose4j.json.internal.json_simple.JSONObject;

import io.swagger.annotations.Api;

@Path("security")
@Api("Security")
@Produces({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON })
@Consumes(MediaType.APPLICATION_JSON)
public class SecurityREST {

	@Inject
	private SecurityContext securityContext;

	@Inject
	private DemoisellePrincipal loggedUser;

	@Inject
	private Token token;

	@Inject
	private DemoiselleSecurityMessages bundle;

	@Inject
	private UserBC usuarioBC;

	@Inject
	private MultiTenantContext multiTenantContext;

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
	@LoggedIn
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

	@POST
	@Path("login")
	public Response testeLogin(Credentials credentials) {

		User usuario = usuarioBC.loadByEmailAndSenha(credentials.getUsername(), credentials.getPassword());

		if (usuario != null) {
			loggedUser.setName(credentials.getUsername());
			loggedUser.setIdentity("" + System.currentTimeMillis());
			ArrayList<String> roles = new ArrayList<>();
			roles.add(usuario.getPerfil());

			Map<String, List<String>> permissions = new HashMap<>();
			ArrayList<String> p1 = new ArrayList<String>();
			p1.add("Alterar");
			ArrayList<String> p2 = new ArrayList<String>();
			p2.add("Consultar");

			permissions.put("Produto", p1);
			permissions.put("Categoria", p2);
			loggedUser.setRoles(roles);
			loggedUser.setPermissions(permissions);

			// Tenant
			loggedUser.addParam("Tenant", multiTenantContext.getTenant().getName());

			securityContext.setUser(loggedUser);
		} else {
			throw new DemoiselleSecurityException(bundle.invalidCredentials(),
					Response.Status.NOT_ACCEPTABLE.getStatusCode());
		}

		return Response.ok().entity("{\"token\":\"" + token.getKey() + "\"}").build();
	}
}
