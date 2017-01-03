/*
 * Demoiselle Framework
 *
 * License: GNU Lesser General Public License (LGPL), version 3 or later.
 * See the lgpl.txt file in the root directory or <https://www.gnu.org/licenses/lgpl.html>.
 */
package org.demoiselle.jee7.example.store.user.service;

import java.util.ArrayList;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.demoiselle.component.tenant.context.MultiTenantContext;
import org.demoiselle.jee.core.api.security.DemoiselleUser;
import org.demoiselle.jee.core.api.security.SecurityContext;
import org.demoiselle.jee.core.api.security.Token;
import org.demoiselle.jee.security.annotation.Cors;
import org.demoiselle.jee.security.annotation.LoggedIn;
import org.demoiselle.jee.security.exception.DemoiselleSecurityException;
import org.demoiselle.jee.security.jwt.impl.DemoiselleSecurityJWTConfig;
import org.demoiselle.jee.security.message.DemoiselleSecurityMessages;
import org.demoiselle.jee7.example.store.user.business.UserBC;
import org.demoiselle.jee7.example.store.user.entity.User;
import org.demoiselle.jee7.example.store.user.security.Credentials;
import org.jose4j.json.internal.json_simple.JSONObject;

import io.swagger.annotations.Api;

@Path("auth")
@Api("Auth")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
@RequestScoped
public class AuthREST {

	@Inject
	private UserBC business;

	@Inject
	private DemoiselleUser loggedUser;

	@Inject
	private DemoiselleSecurityJWTConfig config;

	@Inject
	private MultiTenantContext multiTenantContext;

	@Inject
	private SecurityContext securityContext;

	@Inject
	private DemoiselleSecurityMessages bundle;

	@Inject
	private Token token;

	@POST
	@Path("login")
	public Response login(Credentials credentials) {

		User usuario = business.loadByEmailAndSenha(credentials.getUsername(), credentials.getPassword());

		if (usuario != null) {
			loggedUser.setName(credentials.getUsername());
			loggedUser.setIdentity("" + System.currentTimeMillis());
			ArrayList<String> roles = new ArrayList<>();
			roles.add(usuario.getRole());

			// Resources And Premissions
			loggedUser.addPermission("Produto", "Alterar");
			loggedUser.addPermission("Produto", "Consultar");

			loggedUser.addPermission("Consultar", "Alterar");
			loggedUser.addPermission("Consultar", "Consultar");

			// Role
			loggedUser.addRole(usuario.getRole());

			// Tenant
			loggedUser.addParam("email", usuario.getEmail());
			loggedUser.addParam("fone", usuario.getFone());
			loggedUser.addParam("Tenant", multiTenantContext.getTenant().getName());

			securityContext.setUser(loggedUser);
		} else {
			throw new DemoiselleSecurityException(bundle.invalidCredentials(),
					Response.Status.NOT_ACCEPTABLE.getStatusCode());
		}

		return Response.ok().entity("{\"token\":\"" + token.getKey() + "\"}").build();
	}

	@GET
	@Cors
	@LoggedIn
	public void retoken(@Suspended final AsyncResponse asyncResponse) {
		asyncResponse.resume(doRetoken());
	}

	private Response doRetoken() {
		loggedUser = securityContext.getUser();
		securityContext.setUser(loggedUser);
		return Response.ok().entity("{\"token\":\"" + token.getKey() + "\"}").build();
	}

	@GET
	@Path("publicKey")
	public Response getPublicKey() {
		return Response.ok().entity("{\"publicKey\":\"" + config.getPublicKey() + "\"}").build();
	}

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
	@Path("user")
	@LoggedIn
	public Response user() {
		return Response.ok(loggedUserObject()).build();
	}

}
