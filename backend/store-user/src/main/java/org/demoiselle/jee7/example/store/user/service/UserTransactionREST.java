/*
 * Demoiselle Framework
 *
 * License: GNU Lesser General Public License (LGPL), version 3 or later.
 * See the lgpl.txt file in the root directory or <https://www.gnu.org/licenses/lgpl.html>.
 */
package org.demoiselle.jee7.example.store.user.service;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.demoiselle.jee.rest.annotation.ValidatePayload;
import org.demoiselle.jee.security.annotation.Cors;
import org.demoiselle.jee7.example.store.user.business.UserTransactionBC;
import org.demoiselle.jee7.example.store.user.entity.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Path("userTransactions")
@Api("User Transaction")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
@RequestScoped
public class UserTransactionREST {

	@Inject
	private UserTransactionBC business;

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
