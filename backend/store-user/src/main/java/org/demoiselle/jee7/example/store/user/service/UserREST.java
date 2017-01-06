/*
 * Demoiselle Framework
 *
 * License: GNU Lesser General Public License (LGPL), version 3 or later.
 * See the lgpl.txt file in the root directory or <https://www.gnu.org/licenses/lgpl.html>.
 */
package org.demoiselle.jee7.example.store.user.service;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.demoiselle.jee.persistence.crud.AbstractREST;
import org.demoiselle.jee7.example.store.user.business.UserBC;
import org.demoiselle.jee7.example.store.user.entity.User;
import org.jose4j.json.internal.json_simple.JSONObject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Path("users")
@Api("User")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
@RequestScoped
public class UserREST extends AbstractREST<User, Long> {

	@Inject
	private UserBC business;

	@SuppressWarnings("unchecked")
	@GET
	@Path("usuario/{email}")
	@Transactional
	@ApiOperation(value = "find by email")
	public Response find(@PathParam("email") final String email) {
		User user = business.loadByEmail(email);
		JSONObject json = new JSONObject();
		json.put("id", user.getId());
		json.put("CPF", user.getCpf());
		json.put("email", user.getEmail());
		json.put("fone", user.getFone());
		json.put("name", user.getName());
		json.put("password", user.getPassword());
		json.put("role", user.getRole());
		return Response.ok(json).build();
	}

}