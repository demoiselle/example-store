/*
 * Demoiselle Framework
 *
 * License: GNU Lesser General Public License (LGPL), version 3 or later.
 * See the lgpl.txt file in the root directory or <https://www.gnu.org/licenses/lgpl.html>.
 */
package org.demoiselle.store.usuario.dao;

import org.demoiselle.store.usuario.dao.context.PersistenceContextDAO;
import org.demoiselle.store.usuario.entity.Usuario;

public class UsuarioDAO extends PersistenceContextDAO<Usuario> {

	/**
	 * O Contrutor desta classe precisa ser sem parâmetros por causa do CDI.
	 */
	public UsuarioDAO() {
		super(Usuario.class);
	}

	public Usuario loadByEmailAndSenha(String email, String senha) {
		Usuario u = null;
		try {
			u = getEntityManager()
					.createQuery("select u from Usuario u where u.email = :email AND senha = :senha", Usuario.class)
					.setParameter("email", email).setParameter("senha", senha).getSingleResult();
		} catch (Exception e) {
		}
		return u;
	}

}
