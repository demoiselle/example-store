/*
 * Demoiselle Framework
 *
 * License: GNU Lesser General Public License (LGPL), version 3 or later.
 * See the lgpl.txt file in the root directory or <https://www.gnu.org/licenses/lgpl.html>.
 */
package org.demoiselle.jee7.example.store.user.crud;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.demoiselle.jee.rest.annotation.ValidatePayload;
import org.demoiselle.jee.security.annotation.Cors;

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
	@ApiOperation(value = "Cria objeto utilizando o GenericCRUD (Sem segurança)")
	@Cors
	public void create(T entity) {
		getBusiness().create(entity);
	}

	@PUT
	@Path("{id}")
	@ValidatePayload
	@ApiOperation(value = "Edita objeto utilizando o GenericCRUD (Sem segurança)")
	@Cors
	public void edit(@PathParam("id") Integer id, T entity) {
		getBusiness().edit(id, entity);
	}

	@DELETE
	@Path("{id}")
	@ValidatePayload
	@ApiOperation(value = "Remove objeto utilizando o GenericCRUD (Sem segurança)")
	@Cors
	public void remove(@PathParam("id") Integer id) {
		getBusiness().remove(id);
	}

	@GET
	@Path("{id}")
	@ApiOperation(value = "Busca pelo ID objeto utilizando o GenericCRUD (Sem segurança)")
	@Cors
	public T find(@PathParam("id") Integer id) {
		return getBusiness().find(id);
	}

	@GET
	@Path("{field}/{value}/{start}/{size}")
	@ApiOperation(value = "Busca pelo campo/valor do objeto utilizando o GenericCRUD (Sem segurança)")
	@Cors
	public List<T> findByField(@PathParam("field") String field, @PathParam("value") String value,
			@PathParam("start") int start, @PathParam("size") int size) {
		return getBusiness().find(field, value, "id", "ASC", start, size);
	}

	@PersistenceUnit(unitName = "TenantsPU")
	protected EntityManagerFactory entityManager;

	@GET
	@ApiOperation(value = "Busca todos os objetos utilizando o GenericCRUD (Sem segurança)")
	@Cors
	public List<T> findAll() {
		return getBusiness().findAll();
	}

	@GET
	@Path("{from}/{to}")
	@ApiOperation(value = "Busca por intervalo de ID os objetos utilizando o GenericCRUD (Sem segurança)")
	@Cors
	public List<T> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
		return getBusiness().findRange(new int[] { from, to });
	}

	@GET
	@Path("count")
	@Produces(MediaType.TEXT_PLAIN)
	@ApiOperation(value = "Exibe o total de objetos utilizando o GenericCRUD (Sem segurança)")
	@Cors
	public String count() {
		return String.valueOf(getBusiness().count());
	}

}
