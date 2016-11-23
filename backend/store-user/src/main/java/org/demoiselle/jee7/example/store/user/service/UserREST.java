/*
 * Demoiselle Framework
 *
 * License: GNU Lesser General Public License (LGPL), version 3 or later.
 * See the lgpl.txt file in the root directory or <https://www.gnu.org/licenses/lgpl.html>.
 */
package org.demoiselle.jee7.example.store.user.service;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.demoiselle.jee.rest.annotation.ValidatePayload;
import org.demoiselle.jee.security.annotation.Cors;
import org.demoiselle.jee7.example.store.user.business.UserBC;
import org.demoiselle.jee7.example.store.user.crud.GenericCrudBusiness;
import org.demoiselle.jee7.example.store.user.entity.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Path("user")
@Api("User")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
@RequestScoped
public class UserREST {

	@Inject
	private UserBC business;

	protected GenericCrudBusiness<User> getBusiness() {
		return business;
	}

	@POST
	@ValidatePayload
	@ApiOperation(value = "Cria objeto")
	@Cors
	public void create(User entity) {
		getBusiness().create(entity);
	}

	@PUT
	@Path("{id}")
	@ValidatePayload
	@ApiOperation(value = "Edita objeto")
	@Cors
	public void edit(@PathParam("id") Integer id, User entity) {
		getBusiness().edit(id, entity);
	}

	@DELETE
	@Path("{id}")
	@ValidatePayload
	@ApiOperation(value = "Remove objeto")
	@Cors
	public void remove(@PathParam("id") Integer id) {
		getBusiness().remove(id);
	}

	@GET
	@Path("{id}")
	@ApiOperation(value = "Busca pelo ID objeto")
	@Cors
	public User find(@PathParam("id") Integer id) {
		return getBusiness().find(id);
	}

	@GET
	@Path("{field}/{value}/{start}/{size}")
	@ApiOperation(value = "Busca pelo campo/valor do objeto")
	@Cors
	public List<User> findByField(@PathParam("field") String field, @PathParam("value") String value,
			@PathParam("start") int start, @PathParam("size") int size) {
		return getBusiness().find(field, value, "id", "ASC", start, size);
	}

	@PersistenceUnit(unitName = "TenantsPU")
	protected EntityManagerFactory entityManager;

	@GET
	@ApiOperation(value = "Busca todos os objetos")
	@Cors
	public List<User> findAll() {
		return getBusiness().findAll();
	}

	@GET
	@Path("{from}/{to}")
	@ApiOperation(value = "Busca por intervalo de ID os objetos")
	@Cors
	public List<User> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
		return getBusiness().findRange(new int[] { from, to });
	}

	@GET
	@Path("count")
	@Produces(MediaType.TEXT_PLAIN)
	@ApiOperation(value = "Exibe o total de objetos")
	@Cors
	public String count() {
		return String.valueOf(getBusiness().count());
	}

	@POST
	@ValidatePayload
	@Path("transacional1")
	@ApiOperation(value = "Testa a transação", notes = "Esta operação dará erro mas é utilizada a UserTransaction, quer permite que das 3 inserções somente a primeira seja efetivada. Sendo que a terceira dará erro de UNIQUE KEY VIOLATION do Email do usuário.")
	@Cors
	public void create1(User entity) throws Exception {
		business.createTesteTransacional1(entity);
	}

	@POST
	@ValidatePayload
	@Path("createTest")
	@Cors
	public Response createTest(User entity) throws Exception {
		return Response.ok().entity(business.create(entity)).build();
	}

	@POST
	@ValidatePayload
	@Path("transacional2")
	@ApiOperation(value = "Testa a transação", notes = "Esta operação dará erro pois não o método de business não foi anotado com @Transaction, por isso não possui contexto transacional.")
	@Cors
	public void create2(User entity) {
		business.createTesteTransacional2(entity);
	}

	@POST
	@ValidatePayload
	@Path("transacional3")
	@ApiOperation(value = "Testa a transação", notes = "Esta operação dará erro e NENHUM objeto dos 3 será inserido pois quem está gerenciando a transação (@Transaction) é o container e na última inserção o erro de UNIQUE KEY VIOLATION faz ROLLBACK de tudo.")
	@Cors
	public void create3(User entity) {
		business.createTesteTransacional3(entity);
	}

}
