/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.demoiselle.jee7.example.store.auth.service;

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

import org.demoiselle.jee.core.api.security.DemoisellePrincipal;
import org.demoiselle.jee.core.api.security.SecurityContext;
import org.demoiselle.jee.core.api.security.Token;
import org.demoiselle.jee.rest.annotation.CacheControl;
import org.demoiselle.jee.security.annotation.Cors;
import org.demoiselle.jee.security.annotation.LoggedIn;
import org.demoiselle.jee.security.annotation.NoCors;
import org.demoiselle.jee.security.annotation.RequiredPermission;
import org.demoiselle.jee.security.annotation.RequiredRole;
import org.demoiselle.jee.security.exception.DemoiselleSecurityException;
import org.demoiselle.jee.security.message.DemoiselleSecurityMessages;
import org.demoiselle.jee7.example.store.auth.security.Credentials;

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
    private DemoisellePrincipal loggedUser;

    @Inject
    private Token token;

    @Inject
    private DemoiselleSecurityMessages bundle;

    @POST
    @Path("login")
    public Response testeLogin(Credentials credentials) {
        if (credentials.getPassword().equalsIgnoreCase("123456")) {
            loggedUser.setName(credentials.getUsername());
            loggedUser.setIdentity("" + System.currentTimeMillis());
            loggedUser.addRole("ADMINISTRATOR");
            loggedUser.addRole("MANAGER");
            loggedUser.addPermission("Produto", "Insert");
            loggedUser.addPermission("Categoria", "Consultar");
            securityContext.setUser(loggedUser);
        } else {
            throw new DemoiselleSecurityException(bundle.invalidCredentials(), Response.Status.NOT_ACCEPTABLE.getStatusCode());
        }
        return Response.ok().entity("{\"token\":\"" + token.getKey() + "\"}").build();
    }

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
    @NoCors
    @CacheControl("max-age=600")
    public Response testeGet() {
        return Response.ok("{\"msg\":\"get ok\"}").build();
    }
    
    @POST
    @NoCors
    @CacheControl("max-age=600")
    public Response testePost() {
        return Response.ok("{\"msg\":\"post ok\"}").build();
    }

    @PUT
    @NoCors
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
