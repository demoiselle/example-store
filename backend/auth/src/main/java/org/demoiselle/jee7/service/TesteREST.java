/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.demoiselle.jee7.service;

import io.swagger.annotations.Api;
import javax.ejb.Asynchronous;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import javax.ws.rs.core.Response;
import org.demoiselle.jee.core.interfaces.security.DemoisellePrincipal;
import org.demoiselle.jee.core.interfaces.security.SecurityContext;
import org.demoiselle.jee.core.interfaces.security.Token;
import org.demoiselle.jee.rest.annotation.Cache;
import org.demoiselle.jee.security.annotation.LoggedIn;
import org.demoiselle.jee.security.annotation.RequiredPermission;
import org.demoiselle.jee.security.annotation.RequiredRole;
import org.demoiselle.jee.security.message.DemoiselleSecurityMessages;

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

    @GET
    @Asynchronous
    @Cache("max-age=600")
    @Path("cache")
    public Response testeCache() {
        return Response.ok("{\"msg\":\"cache ok\"}").build();
    }

    @GET
    @Asynchronous
    @Path("cors")
    //@CorsAllowMethods
    //@CorsAllowOrigin("http://localhost:8080/")
    public Response testeCors() {
        return Response.ok("{\"msg\":\"cors ok\"}").build();
    }

    @GET
    @Asynchronous
    @Path("sem")
    public Response testeSem() {
        return Response.ok().entity("{\"msg\":\"Foi sem\"}").build();
    }

    @GET
    @Asynchronous
    @Path("com")
    @LoggedIn
    public Response testeCom() {
        return Response.ok().entity(securityContext.getUser().toString()).build();
    }

    @GET
    @Asynchronous
    @Path("role/ok")
    @RequiredRole("ADMINISTRATOR")
    public Response testeRoleOK() {
        return Response.ok().entity(securityContext.getUser().toString()).build();
    }

    @GET
    @Asynchronous
    @Path("role/error")
    @RequiredRole("USUARIO")
    public Response testeRoleErro() {
        return Response.ok().entity(securityContext.getUser().toString()).build();
    }

    @GET
    @Asynchronous
    @Path("permission/ok")
    @RequiredPermission(resource = "Categoria", operation = "Consultar")
    public Response testePermissionOK() {
        return Response.ok().entity(securityContext.getUser().toString()).build();
    }

    @GET
    @Asynchronous
    @Path("permission/error")
    @RequiredPermission(resource = "Produto", operation = "Incluir")
    public Response testePermissionErro() {
        return Response.ok().entity(securityContext.getUser().toString()).build();
    }

}
