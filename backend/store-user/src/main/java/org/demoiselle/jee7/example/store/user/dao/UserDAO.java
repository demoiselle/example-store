/*
 * Demoiselle Framework
 *
 * License: GNU Lesser General Public License (LGPL), version 3 or later.
 * See the lgpl.txt file in the root directory or <https://www.gnu.org/licenses/lgpl.html>.
 */
package org.demoiselle.jee7.example.store.user.dao;

import org.demoiselle.jee7.example.store.user.dao.context.PersistenceContextDAO;
import org.demoiselle.jee7.example.store.user.entity.User;

public class UserDAO extends PersistenceContextDAO<User> {

	/**
	 * O Contrutor desta classe precisa ser sem parâmetros por causa do CDI.
	 */
	public UserDAO() {
		super(User.class);
	}

	public User loadByEmailAndSenha(String email, String senha) {
		User u = null;
		try {
			u = getEntityManager()
					.createQuery("select u from Usuario u where u.email = :email AND senha = :senha", User.class)
					.setParameter("email", email).setParameter("senha", senha).getSingleResult();
		} catch (Exception e) {
		}
		return u;
	}

}
