package org.demoiselle.store.usuario.service;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.demoiselle.jee.core.messages.DemoiselleMessages;
import org.demoiselle.store.usuario.configuration.AppConfiguration;

@Path("info")
public class InfoREST {

	@Inject
	private DemoiselleMessages messages;

	@Inject
	private AppConfiguration configuration;

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
		return configuration.appSearchUrl();
	}

}