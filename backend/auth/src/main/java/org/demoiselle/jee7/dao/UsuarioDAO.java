/*
 * Demoiselle Framework
 *
 * License: GNU Lesser General Public License (LGPL), version 3 or later.
 * See the lgpl.txt file in the root directory or <https://www.gnu.org/licenses/lgpl.html>.
 */
package org.demoiselle.jee7.dao;

import java.util.List;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.demoiselle.jee7.dao.context.PersistenceContextDAO;
import org.demoiselle.jee7.entity.Usuario;

public class UsuarioDAO extends PersistenceContextDAO<Usuario> {

    /**
     * O Contrutor desta classe precisa ser sem parâmetros por causa do CDI.
     */
    public UsuarioDAO() {
        super(Usuario.class);
    }

    public Usuario verifyEmail(String email, String password) {

        CriteriaBuilder builder = emEntity.getCriteriaBuilder();
        CriteriaQuery<Usuario> query = builder.createQuery(Usuario.class);
        Root<Usuario> from = query.from(Usuario.class);
        TypedQuery<Usuario> typedQuery = emEntity.createQuery(
                query.select(from)
                .where(builder.equal(from.get("email"), email))
        );

        Usuario usu = typedQuery.getResultList().get(0);

        return ((usu != null) && (usu.getSenha().equalsIgnoreCase(password))) ? usu : null;

        //Usuario usu = find("email", email, "email", "ASC", 1, 1).get(0);
        //return null;//((usu != null) && (usu.getSenha().equalsIgnoreCase(password))) ? usu : null;
    }

}
