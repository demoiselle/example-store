/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.demoiselle.jee7.example.store.auth.service;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.ejb.Asynchronous;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Response;

import org.demoiselle.jee.core.api.security.DemoisellePrincipal;
import org.demoiselle.jee.core.api.security.SecurityContext;
import org.demoiselle.jee.core.api.security.Token;
import org.demoiselle.jee.security.annotation.Cors;
import org.demoiselle.jee.security.annotation.LoggedIn;
import org.demoiselle.jee.security.exception.DemoiselleSecurityException;
import org.demoiselle.jee.security.jwt.impl.DemoiselleSecurityJWTConfig;
import org.demoiselle.jee.security.message.DemoiselleSecurityMessages;
import org.demoiselle.jee7.example.store.auth.dao.UsuarioDAO;
import org.demoiselle.jee7.example.store.auth.entity.Usuario;
import org.demoiselle.jee7.example.store.auth.security.Credentials;

import io.swagger.annotations.Api;

/**
 *
 * @author 70744416353
 */
@Api("Auth")
@Path("auth")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class AuthREST {

	@Inject
	private UsuarioDAO dao;

	@Inject
	private DemoiselleSecurityJWTConfig config;

	@Inject
	private SecurityContext securityContext;

	@Inject
	private DemoisellePrincipal loggedUser;

	@Inject
	private Token token;

	@Inject
	private DemoiselleSecurityMessages bundle;

	@POST
	@Cors
	@Asynchronous
	public void testeLogin(@Suspended final AsyncResponse asyncResponse, Credentials credentials) {
		asyncResponse.resume(doLogin(credentials));
	}

	private Response doLogin(Credentials credentials) {
		Usuario usu = dao.verifyEmail(credentials.getUsername(), credentials.getPassword());
		if (usu != null) {
			loggedUser.setName(usu.getNome());
			loggedUser.setIdentity("" + usu.getId());
			loggedUser.addRole(usu.getPerfil());
			loggedUser.addPermission("SWAGGER", "LIST");
			loggedUser.addParam("Fone", usu.getFone());
			loggedUser.addParam("Email", usu.getEmail());
			securityContext.setUser(loggedUser);
		} else {
			throw new DemoiselleSecurityException(bundle.invalidCredentials(),
					Response.Status.UNAUTHORIZED.getStatusCode());
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
}
