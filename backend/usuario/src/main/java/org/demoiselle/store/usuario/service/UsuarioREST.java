/*
 * Demoiselle Framework
 *
 * License: GNU Lesser General Public License (LGPL), version 3 or later.
 * See the lgpl.txt file in the root directory or <https://www.gnu.org/licenses/lgpl.html>.
 */
package org.demoiselle.store.usuario.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.demoiselle.jee.ws.jaxrs.interceptor.ValidatePayload;
import org.demoiselle.store.usuario.business.GenericCrudBusiness;
import org.demoiselle.store.usuario.business.UsuarioBC;
import org.demoiselle.store.usuario.entity.Usuario;

import io.swagger.annotations.ApiOperation;

@Stateless
@Path("usuario")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class UsuarioREST extends GenericCrudWithoutSecurityREST<Usuario> {

	@Inject
	private UsuarioBC business;

	protected GenericCrudBusiness<Usuario> getBusiness() {
		return business;
	}

	@POST
	@ValidatePayload
	@Path("transacional1")
	@ApiOperation(value = "Testa a transação (JTA/JPA)", notes = "Esta operação dará erro mas é utilizada a UserTransaction, quer permite que das 3 inserções somente a primeira seja efetivada. Sendo que a terceira dará erro de UNIQUE KEY VIOLATION do Email do usuário.")
	public void create1(Usuario entity) throws SecurityException, IllegalStateException, RollbackException,
			HeuristicMixedException, HeuristicRollbackException, SystemException, NotSupportedException {
		business.createTesteTransacional1(entity);
	}

	@POST
	@ValidatePayload
	@Path("transacional2")
	@ApiOperation(value = "Testa a transação (JTA/JPA)", notes = "Esta operação dará erro pois não o método de business não foi anotado com @Transaction, por isso não possui contexto transacional.")
	public void create2(Usuario entity) {
		business.createTesteTransacional2(entity);
	}

	@POST
	@ValidatePayload
	@Path("transacional3")
	@ApiOperation(value = "Testa a transação (JTA/JPA)", notes = "Esta operação dará erro e NENHUM objeto dos 3 será inserido pois quem está gerenciando a transação (@Transaction) é o container e na última inserção o erro de UNIQUE KEY VIOLATION faz ROLLBACK de tudo.")
	public void create3(Usuario entity) {
		business.createTesteTransacional3(entity);
	}

}
