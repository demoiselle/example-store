/*
 * Demoiselle Framework
 *
 * License: GNU Lesser General Public License (LGPL), version 3 or later.
 * See the lgpl.txt file in the root directory or <https://www.gnu.org/licenses/lgpl.html>.
 */
package org.demoiselle.jee7.example.store.auth.dao;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.core.Response;

import org.demoiselle.jee.persistence.crud.AbstractDAO;
import org.demoiselle.jee.security.exception.DemoiselleSecurityException;
import org.demoiselle.jee7.example.store.auth.entity.Usuario;

public class UsuarioDAO extends AbstractDAO<Usuario> {

    public Usuario verifyEmail(String email, String password) {

        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Usuario> query = builder.createQuery(Usuario.class);
        Root<Usuario> from = query.from(Usuario.class);
        TypedQuery<Usuario> typedQuery = getEntityManager().createQuery(
                query.select(from)
                .where(builder.equal(from.get("email"), email))
        );

        if (typedQuery.getResultList().isEmpty()) {
            throw new DemoiselleSecurityException("Usuário não existe", Response.Status.UNAUTHORIZED.getStatusCode());
        }

        Usuario usu = typedQuery.getResultList().get(0);

        if (usu == null) {
            throw new DemoiselleSecurityException("Usuário não existe", Response.Status.UNAUTHORIZED.getStatusCode());
        }

        if (!usu.getSenha().equalsIgnoreCase(password)) {
            throw new DemoiselleSecurityException("Senha incorreta", Response.Status.UNAUTHORIZED.getStatusCode());
        }

        return usu;

        //Usuario usu = find("email", email, "email", "ASC", 1, 1).get(0);
        //return null;//((usu != null) && (usu.getSenha().equalsIgnoreCase(password))) ? usu : null;
    }

}
