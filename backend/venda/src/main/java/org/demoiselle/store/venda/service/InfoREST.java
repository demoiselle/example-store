package org.demoiselle.store.venda.service;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.demoiselle.jee.core.message.DemoiselleMessage;
import org.demoiselle.store.venda.configuration.AppConfiguration;

import io.swagger.annotations.Api;

@Path("info")
@Api("Informações do Sistema")
public class InfoREST {

	@Inject
	private DemoiselleMessage messages;

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
		return configuration.getAppSearchUrl();
	}

}