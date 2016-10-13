/*
 * Demoiselle Framework
 *
 * License: GNU Lesser General Public License (LGPL), version 3 or later.
 * See the lgpl.txt file in the root directory or <https://www.gnu.org/licenses/lgpl.html>.
 */
package org.demoiselle.jee7.crud;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.demoiselle.jee.rest.annotation.ValidatePayload;

import io.swagger.annotations.ApiOperation;

/**
 * Ao utilizar essa generalização de CRUD existem 3 efeitos importantes: 1. não
 * é gerada a documentação do SWAGGER 2. não é aplicada segurança nos serviços
 * 3. são expostos TODOS os serviços do CRUD
 *
 * @author SERPRO
 *
 * @param <T>
 */
public abstract class GenericCrudWithoutSecurityREST<T> {

    protected abstract GenericCrudBusiness<T> getBusiness();

    @POST
    @ValidatePayload
    @ApiOperation(value = "Cria usuário utilizando o GenericCRUD (Sem segurança)")
    public void create(T entity) {
        getBusiness().create(entity);
    }

    @PUT
    @Path("{id}")
    @ValidatePayload
    @ApiOperation(value = "Edita usuário utilizando o GenericCRUD (Sem segurança)")
    public void edit(@PathParam("id") Integer id, T entity) {
        getBusiness().edit(id, entity);
    }

    @DELETE
    @Path("{id}")
    @ValidatePayload
    @ApiOperation(value = "Remove usuário utilizando o GenericCRUD (Sem segurança)")
    public void remove(@PathParam("id") Integer id) {
        getBusiness().remove(id);
    }

    @GET
    @Path("{id}")
    @ApiOperation(value = "Busca pelo ID usuário utilizando o GenericCRUD (Sem segurança)")
    public T find(@PathParam("id") Integer id) {
        return getBusiness().find(id);
    }

    @GET
    @ApiOperation(value = "Busca todos os usuários utilizando o GenericCRUD (Sem segurança)")
    public List<T> findAll() {
        return getBusiness().findAll();
    }

    @GET
    @Path("{from}/{to}")
    @ApiOperation(value = "Busca por intervalo de ID os usuários utilizando o GenericCRUD (Sem segurança)")
    public List<T> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return getBusiness().findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    @ApiOperation(value = "Exibe o total de usuários utilizando o GenericCRUD (Sem segurança)")
    public String count() {
        return String.valueOf(getBusiness().count());
    }

//    @GET
//    @Path("list/{from}/{size}/{sort}/{order}")
//    @ApiOperation(value = "Busca usuários utilizando o GenericCRUD (Sem segurança)")
//    public Response list(@QueryParam("search") String search, @QueryParam("fields") String fields,
//            @PathParam("sort") String sort, @PathParam("order") String order, @PathParam("from") int from,
//            @PathParam("size") int size) {
//        return Response.ok().entity(getBusiness().pageResult(sort, order, from, size, search, fields, null)).build();
//    }
}
