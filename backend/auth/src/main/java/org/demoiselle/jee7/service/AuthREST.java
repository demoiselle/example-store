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
import org.demoiselle.jee.core.interfaces.security.TokensManager;
import org.demoiselle.jee.security.annotation.RequiredPermission;
import org.demoiselle.jee.security.exception.DemoiselleSecurityException;
import org.demoiselle.jee.security.jwt.impl.TokensManagerImpl;
import org.demoiselle.jee.security.message.DemoiselleSecurityMessages;
import org.demoiselle.jee7.security.Credentials;

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
    private TokensManager tm;

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
        return Response.ok().entity(token).build();
    }

    @GET
    @Path("publicKey")
    public Response getPublicKey() {
        return Response.ok().entity(tm.getPublicKey()).build();
    }
}
