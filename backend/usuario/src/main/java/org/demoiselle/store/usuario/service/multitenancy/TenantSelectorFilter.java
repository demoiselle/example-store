/*
 * Demoiselle Framework
 *
 * License: GNU Lesser General Public License (LGPL), version 3 or later.
 * See the lgpl.txt file in the root directory or <https://www.gnu.org/licenses/lgpl.html>.
 */
package org.demoiselle.store.usuario.service.multitenancy;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

import org.demoiselle.store.usuario.dao.multitenancy.MultiTenancyContext;
import org.demoiselle.store.usuario.entity.Tenant;

@Provider
@PreMatching
public class TenantSelectorFilter implements ContainerRequestFilter {

	@Inject
	private Logger log;

	// @Inject
	// private AppConfiguration configuration;

	@PersistenceUnit(unitName = "TenantsPU")
	protected EntityManagerFactory entityManager;

	@PersistenceContext(unitName = "MasterPU")
	protected EntityManager entityManagerMaster;

	// @Inject
	// private TenancyDAO dao;

	@Inject
	private MultiTenancyContext multitenancyContext;

	@PostConstruct
	public void init() {
		log.info("Demoiselle Module - Multi Tenancy");
	}

	@Override
	@SuppressWarnings("unchecked")
	public void filter(ContainerRequestContext requestContext) throws IOException {

		Tenant tenantUrl = new Tenant(requestContext.getUriInfo().getPathSegments().get(0).toString());

		// Pega os tenants do banco de dados
		// List<Tenant> tenantsDatabase = dao.find("name", tenantUrl.getName(),
		// "id", "ASC", 0, 1);
		Query query = entityManagerMaster.createQuery("select u from Tenant u where u.name = :value", Tenant.class);
		query.setParameter("value", tenantUrl.getName());
		query.setHint("org.hibernate.cacheable", "true");
		// Cache de 60s (60000ms)
		query.setHint("javax.persistence.query.timeout", 60000);

		List<Tenant> list = query.getResultList();

		String tenantName = "master";

		if (list.size() == 1) {

			Tenant tenant = list.get(0); // (Tenant) query.getSingleResult();
			tenantName = tenant.getName();

			// Altera a URL para ir para o local correto
			String newURi = "";
			for (int i = 1; i < requestContext.getUriInfo().getPathSegments().size(); i++) {
				newURi += requestContext.getUriInfo().getPathSegments().get(i).toString() + "/";
			}

			try {
				requestContext.setRequestUri(new URI(newURi));
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}

			// System.out.println("Local alterado [" + tenantName + "]: " +
			// requestContext.getUriInfo().getPath());

		} else {
			// log.info("Vai para o local normal: " +
			// requestContext.getUriInfo().getPath());
		}

		multitenancyContext.setTenant(tenantName);

	}

}
