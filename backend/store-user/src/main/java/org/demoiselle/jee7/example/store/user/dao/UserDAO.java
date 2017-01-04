/*
 * Demoiselle Framework
 *
 * License: GNU Lesser General Public License (LGPL), version 3 or later.
 * See the lgpl.txt file in the root directory or <https://www.gnu.org/licenses/lgpl.html>.
 */
package org.demoiselle.jee7.example.store.user.dao;

import org.demoiselle.jee7.example.store.user.dao.entityManager.EntityManagerDAO;
import org.demoiselle.jee7.example.store.user.entity.User;

public class UserDAO extends EntityManagerDAO<User> {

	/**
	 * O Contrutor desta classe precisa ser sem parâmetros por causa do CDI.
	 */
	public UserDAO() {
	}

	public User loadByEmailAndSenha(String email, String password) {
		User u = null;
		try {
			u = getEntityManager()
					.createQuery("select u from User u where u.email = :email AND password = :password", User.class)
					.setParameter("email", email).setParameter("password", password).getSingleResult();
		} catch (Exception e) {
		}
		return u;
	}

	public User loadByEmail(String email) {
		User u = null;
		try {
			u = getEntityManager()
					.createQuery("select u from User u where u.email = :email", User.class)
					.setParameter("email", email).getSingleResult();
		} catch (Exception e) {
		}
		return u;
	}
}
