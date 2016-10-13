/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.demoiselle.jee7.service;

import io.swagger.annotations.Api;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import javax.ws.rs.core.Response;
import org.demoiselle.jee.core.api.security.SecurityContext;
import org.demoiselle.jee.security.annotation.LoggedIn;

/**
 *
 * @author 70744416353
 */
@Api("Teste")
@Path("test")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class ProdutoREST {

    @Inject
    private SecurityContext securityContext;

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
}
