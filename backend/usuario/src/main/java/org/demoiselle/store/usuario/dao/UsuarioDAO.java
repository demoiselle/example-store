/*
 * Demoiselle Framework
 *
 * License: GNU Lesser General Public License (LGPL), version 3 or later.
 * See the lgpl.txt file in the root directory or <https://www.gnu.org/licenses/lgpl.html>.
 */
package org.demoiselle.store.usuario.dao;

import org.demoiselle.store.usuario.entity.Usuario;

public class UsuarioDAO extends PersistenceContextDAO<Usuario> {

	/**
	 * O Contrutor desta classe precisa ser sem parâmetros por causa do CDI.
	 */
	public UsuarioDAO() {
		super(Usuario.class);
	}

}
