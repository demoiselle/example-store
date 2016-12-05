package org.demoiselle.jee7.example.store.product.service;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.demoiselle.jee.core.message.DemoiselleMessage;
import org.demoiselle.jee.security.annotation.Cors;
import org.jose4j.json.internal.json_simple.JSONObject;

import io.swagger.annotations.Api;

@Path("info")
@Api("System Information")
public class InfoREST {

	@Inject
	private DemoiselleMessage messages;

	@SuppressWarnings("unchecked")
	@GET
	@Path("ping")
	@Cors
	@Produces({ MediaType.APPLICATION_JSON })
	public Response ping() throws Exception {
		JSONObject json = new JSONObject();
		json.put("result", "pong");

		return Response.ok().entity(json).build();
	}

	@GET
	@Path("version")
	@Produces("text/plain")
	public String getMessage() throws Exception {
		return messages.version();
	}

}