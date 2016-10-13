/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.demoiselle.jee7.service;

import io.swagger.annotations.Api;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.Asynchronous;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import javax.ws.rs.core.Response;
import org.demoiselle.jee.core.api.security.DemoisellePrincipal;
import org.demoiselle.jee.core.api.security.SecurityContext;
import org.demoiselle.jee.core.api.security.Token;
import org.demoiselle.jee.rest.annotation.Cache;
import org.demoiselle.jee.security.annotation.LoggedIn;
import org.demoiselle.jee.security.annotation.RequiredPermission;
import org.demoiselle.jee.security.annotation.RequiredRole;
import org.demoiselle.jee.security.exception.DemoiselleSecurityException;
import org.demoiselle.jee.security.message.DemoiselleSecurityMessages;
import org.demoiselle.jee7.security.Credentials;

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
            ArrayList<String> roles = new ArrayList<String>();
            roles.add("ADMINISTRATOR");
            roles.add("MANAGER");
            Map<String, String> permissions = new HashMap<String, String>();
            permissions.put("Produto", "Insert");
            permissions.put("Categoria", "Consultar");
            loggedUser.setRoles(roles);
            loggedUser.setPermissions(permissions);
            securityContext.setUser(loggedUser);
        } else {
            throw new DemoiselleSecurityException(bundle.invalidCredentials(), Response.Status.NOT_ACCEPTABLE.getStatusCode());
        }
        return Response.ok().entity("{\"token\":\"" + token.getKey() + "\"}").build();
    }
    
    @GET
    @LoggedIn
    @Cache("max-age=600")
    @Path("cache")
    public void testeCache(@Suspended final AsyncResponse asyncResponse) {
        asyncResponse.resume(doCache());
    }

    private Response doCache() {
       return Response.ok("{\"msg\":\"cache ok\"}").build();
    }

    @GET
    @Path("cors")
    public Response testeCors() {
        return Response.ok("{\"msg\":\"cors ok\"}").build();
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

}
