package org.demoiselle.jee7.example.store.product.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.demoiselle.jee.core.api.crud.Result;
import org.demoiselle.jee.rest.annotation.ValidatePayload;
import org.demoiselle.jee.security.annotation.Cors;
import org.demoiselle.tenant.hibernate.business.TenantManager;
import org.demoiselle.tenant.hibernate.entity.Tenant;

@Path("tenants")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class TenantREST {

	@Inject
	private TenantManager tenantManager;

	private Logger logger;

	@GET
	@Cors
	public Result listAllTenants() throws Exception {
		return tenantManager.find();
	}

	@DELETE
	@Path("{name}")
	@Cors
	public Response deleteTenant(@PathParam("name") String name) throws Exception {
		try {
			// Load tenant
			Tenant tenant = tenantManager.findByName(name);

			if (tenant == null) {
				throw new NotFoundException();
			}

			tenantManager.removeTenant(tenant);

			return Response.ok().build();
		} catch (final Exception e) {
			logger.log(Level.SEVERE, "Error trying to DELETE Tenant", e);
			return Response.serverError().build();
		}
	}

	@GET
	@Path("context")
	@Cors
	public Response multitenancyContext() throws Exception {
		return Response.ok().entity(tenantManager.getTenantName()).build();
	}

	@POST
	@Cors
	@ValidatePayload
	public Response createTenant(Tenant tenant) throws Exception {
		try {
			tenantManager.createTenant(tenant);
			return Response.ok().build();
		} catch (final Exception e) {
			logger.log(Level.SEVERE, "Error trying to CREATE Tenant", e);
			return Response.serverError().build();
		}
	}

}
