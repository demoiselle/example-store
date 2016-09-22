package org.demoiselle.store.usuario.service;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.demoiselle.jee.core.messages.DemoiselleMessages;

@Path("info")
public class InfoREST {

	@Inject
	private DemoiselleMessages messages;

	@GET
	@Path("version")
	@Produces("text/plain")
	public String getMessage() throws Exception {
		return messages.version();
	}

}