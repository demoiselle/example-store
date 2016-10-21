/*
 * Demoiselle Framework
 *
 * License: GNU Lesser General Public License (LGPL), version 3 or later.
 * See the lgpl.txt file in the root directory or <https://www.gnu.org/licenses/lgpl.html>.
 */
package org.demoiselle.store.usuario.service.multitenancy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.demoiselle.jee.security.annotation.Cors;
import org.demoiselle.store.usuario.business.TenancyBC;
import org.demoiselle.store.usuario.configuration.AppConfiguration;
import org.demoiselle.store.usuario.entity.Tenant;

import io.swagger.annotations.Api;

@Path("multiTenancy")
@Api("Multi-Tenancy")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class MultiTenancyREST {

	@Inject
	private TenancyBC business;

	private DataSource dataSource;

	private Logger logger;

	@Inject
	private AppConfiguration configuration;

	@GET
	@Cors
	public Response listAllTenants() throws Exception {
		return Response.ok().entity(business.listAllTenants()).build();
	}

	@DELETE
	@Path("deleteTenant/{id}")
	@Cors
	public Response deleteTenant(@PathParam("id") Integer id) throws Exception {
		try {

			// Add Tenancy in table/master schema
			Tenant t = business.find(id);
			business.remove(t.getId());

			// Create Schema
			final Context init = new InitialContext();
			dataSource = (DataSource) init.lookup(configuration.getMultitenacyTenantsDatabaseDatasource());

			Connection conn = dataSource.getConnection();

			// Cria o BANCO/SCHEMA
			conn.createStatement().execute(configuration.getMultitenacyDropDatabaseSQL() + " " + t.getName());

			// Como a conexão esta fora de contexto é importante fechar ela aqui
			if (!conn.isClosed()) {
				conn.close();
			}

			return Response.ok().build();

		} catch (final Exception e) {
			logger.log(Level.INFO, "Error trying to alter schema", e);
			return Response.serverError().build();
		}
	}

	@GET
	@Path("multitenancyContext")
	@Cors
	public Response multitenancyContext() throws Exception {
		return Response.ok().entity(business.getMultitenancyContext()).build();
	}

	@POST
	@Path("createTenant/{name}")
	@Cors
	public Response createTenant(@PathParam("name") String name) throws Exception {
		try {
			// Add Tenancy in table/master schema
			Tenant t = new Tenant();
			t.setName(name);
			business.create(t);

			// Create Schema
			final Context init = new InitialContext();
			dataSource = (DataSource) init.lookup(configuration.getMultitenacyTenantsDatabaseDatasource());

			Connection conn = dataSource.getConnection();

			// Cria o BANCO/SCHEMA
			conn.createStatement().execute(configuration.getMultitenacyCreateDatabaseSQL() + " " + t.getName());

			// Usa o BANCO/SCHEMA (MySQL)
			conn.createStatement().execute(configuration.getMultitenacySetDatabaseSQL() + " " + t.getName());

			// Roda o DDL - DROP
			dropDatabase(conn);

			// Roda o DDL - CREATE
			createDatabase(conn);

			// Como a conexão esta fora de contexto é importante fechar ela aqui
			if (!conn.isClosed()) {
				conn.close();
			}

			return Response.ok().build();

		} catch (final Exception e) {
			logger.log(Level.INFO, "Error trying to alter schema", e);
			return Response.serverError().build();
		}

	}

	private void dropDatabase(Connection conn) throws SQLException {
		String filename = configuration.getMultitenacyDropDatabaseDDL();
		List<String> ddl = getDDLString(filename);
		for (String ddlLine : ddl) {
			conn.createStatement().execute(ddlLine);
		}
	}

	private void createDatabase(Connection conn) throws SQLException {
		String filename = configuration.getMultitenacyCreateDatabaseDDL();
		List<String> ddl = getDDLString(filename);
		for (String ddlLine : ddl) {
			conn.createStatement().execute(ddlLine);
		}
	}

	private List<String> getDDLString(String filename) {
		List<String> records = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = reader.readLine()) != null) {
				records.add(line);
			}
			reader.close();
			return records;
		} catch (Exception e) {
			System.err.format("Exception occurred trying to read '%s'.", filename);
			// e.printStackTrace();
			return null;
		}
	}

}
