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
import org.demoiselle.jee.security.annotation.LoggedIn;
import org.demoiselle.jee7.dao.UsuarioDAO;

/**
 *
 * @author 70744416353
 */
@Api("Usuario")
@Path("usuario")
@LoggedIn
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class UsuarioREST {//extends AbstractREST<Usuario> {

    @Inject
    private UsuarioDAO dao;

    @GET
    public Response list() {
        return Response.ok().entity(dao.findAll()).build();
    }
}
