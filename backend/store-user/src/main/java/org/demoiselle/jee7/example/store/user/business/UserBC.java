/*
 * Demoiselle Framework
 *
 * License: GNU Lesser General Public License (LGPL), version 3 or later.
 * See the lgpl.txt file in the root directory or <https://www.gnu.org/licenses/lgpl.html>.
 */
package org.demoiselle.jee7.example.store.user.business;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.demoiselle.jee.persistence.crud.AbstractBusiness;
import org.demoiselle.jee.security.exception.DemoiselleSecurityException;
import org.demoiselle.jee7.example.store.user.dao.UserDAO;
import org.demoiselle.jee7.example.store.user.entity.User;

@Stateless
@TransactionManagement(value = TransactionManagementType.CONTAINER)
public class UserBC extends AbstractBusiness<User, Long> {

	@Inject
	private UserDAO dao;

	public User loadByEmailAndSenha(String email, String senha) {
		User u = dao.loadByEmailAndSenha(email, senha);
		if (u == null) {
			throw new DemoiselleSecurityException("Usuário não existe", Response.Status.UNAUTHORIZED.getStatusCode());
		}
		return u;
	}

}
