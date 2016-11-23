package org.demoiselle.jee7.example.store.user.service;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.demoiselle.jee.core.message.DemoiselleMessage;
import org.demoiselle.jee.security.annotation.Cors;
import org.demoiselle.jee7.example.store.user.configuration.AppConfiguration;
import org.jose4j.json.internal.json_simple.JSONObject;

import io.swagger.annotations.Api;

@Path("info")
@Api("Informações do Sistema")
public class InfoREST {

	@Inject
	private DemoiselleMessage messages;

	@Inject
	private AppConfiguration configuration;

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

	@GET
	@Path("search/url")
	@Produces("text/plain")
	public String getSearchUrl() throws Exception {
		return configuration.getAppSearchUrl();
	}

}