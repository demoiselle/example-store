/*
 * Demoiselle Framework
 *
 * License: GNU Lesser General Public License (LGPL), version 3 or later.
 * See the lgpl.txt file in the root directory or <https://www.gnu.org/licenses/lgpl.html>.
 */
package org.demoiselle.jee7.example.store.user.service;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.demoiselle.jee.core.api.crud.Result;
import org.demoiselle.jee.persistence.crud.AbstractREST;
import org.demoiselle.jee7.example.store.user.entity.User;

import io.swagger.annotations.Api;

@Path("users")
@Api("User")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
@RequestScoped
public class UserREST extends AbstractREST<User, Long> {


}