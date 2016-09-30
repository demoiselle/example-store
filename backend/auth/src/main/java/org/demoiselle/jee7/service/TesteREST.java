/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.demoiselle.jee7.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import javax.ws.rs.core.Response;
import org.demoiselle.jee.core.interfaces.security.DemoisellePrincipal;
import org.demoiselle.jee.core.interfaces.security.SecurityContext;
import org.demoiselle.jee.core.interfaces.security.Token;
import org.demoiselle.jee.security.annotation.LoggedIn;
import org.demoiselle.jee.security.annotation.RequiredPermission;
import org.demoiselle.jee.security.annotation.RequiredRole;
import org.demoiselle.jee.security.exception.DemoiselleSecurityException;
import org.demoiselle.jee.security.message.DemoiselleSecurityMessages;
import org.demoiselle.jee.ws.jaxrs.annotation.Cache;
import org.demoiselle.jee.ws.jaxrs.annotation.CorsAllowMethods;
import org.demoiselle.jee.ws.jaxrs.annotation.CorsAllowOrigin;

import org.demoiselle.jee7.security.Credentials;

/**
 *
 * @author 70744416353
 */
@Path("teste")
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
    @Cache("max-age=600")
    @Path("cache")
    public Response testeCache() {
        return Response.ok("Foi com cache de 1000s").build();
    }

    @GET
    @Path("cors")
    @CorsAllowMethods
    @CorsAllowOrigin("http://localhost:8080/")
    public Response testeCors() {
        return Response.ok("Foi com cors").build();
    }

    @GET
    @Path("sem")
    public String testeSem() {
        return "Foi sem";
    }

    @GET
    @Path("com")
    @LoggedIn
    public String testeCom() {
        return securityContext.getUser().toString();
    }

    @GET
    @Path("role/ok")
    @RequiredRole("ADMINISTRATOR")
    public String testeRoleOK() {
        return securityContext.getUser().toString();
    }

    @GET
    @Path("role/error")
    @RequiredRole("USUARIO")
    public String testeRoleErro() {
        return securityContext.getUser().toString();
    }

    @GET
    @Path("permission/ok")
    @RequiredPermission(resource = "Categoria", operation = "Consultar")
    public String testePermissionOK() {
        return securityContext.getUser().toString();
    }

    @GET
    @Path("permission/error")
    @RequiredPermission(resource = "Produto", operation = "Incluir")
    public String testePermissionErro() {
        return securityContext.getUser().toString();
    }

    @POST
    @Path("login")
    public String testeLogin(Credentials credentials) {
        if (credentials.getUsername().equalsIgnoreCase("Gladson") && credentials.getPassword().equalsIgnoreCase("123456")) {
            loggedUser.setName(credentials.getUsername());
            loggedUser.setId("" + System.currentTimeMillis());
            ArrayList<String> roles = new ArrayList<>();
            roles.add("ADMINISTRATOR");
            roles.add("MANAGER");
            Map<String, String> permissions = new HashMap<>();
            permissions.put("Produto", "Alterar");
            permissions.put("Categoria", "Consultar");
            loggedUser.setRoles(roles);
            loggedUser.setPermissions(permissions);
            securityContext.setUser(loggedUser);
        } else {
            throw new DemoiselleSecurityException(bundle.invalidCredentials(), Response.Status.NOT_ACCEPTABLE.getStatusCode());
        }
        return token.getKey();
    }

}
