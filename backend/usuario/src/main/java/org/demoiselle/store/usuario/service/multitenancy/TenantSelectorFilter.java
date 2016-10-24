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
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

import org.demoiselle.store.usuario.configuration.AppConfiguration;
import org.demoiselle.store.usuario.dao.multitenancy.MultiTenantContext;
import org.demoiselle.store.usuario.entity.Tenant;

@Provider
@PreMatching
public class TenantSelectorFilter implements ContainerRequestFilter {

	@Inject
	private Logger log;

	@Inject
	private AppConfiguration configuration;

	@PersistenceContext(unitName = "MasterPU")
	protected EntityManager entityManagerMaster;

	@Inject
	private MultiTenantContext multitenancyContext;

	@PostConstruct
	public void init() {
		log.info("Demoiselle Module - Multi Tenancy");
	}

	@Override
	@SuppressWarnings("unchecked")
	public void filter(ContainerRequestContext requestContext) throws IOException {

		String tenantNameUrl = requestContext.getUriInfo().getPathSegments().get(0).toString();
		Tenant tenant = null;

		// Pega os tenants do banco de dados
		Query query = entityManagerMaster.createQuery("select u from Tenant u where u.name = :value", Tenant.class);
		query.setParameter("value", tenantNameUrl);
		query.setHint("org.hibernate.cacheable", "true");
		// Cache de 60s (60000ms)
		query.setHint("javax.persistence.query.timeout", 60000);

		List<Tenant> list = query.getResultList();

		if (list.size() == 1) {

			tenant = list.get(0); // (Tenant) query.getSingleResult();

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
			tenant = new Tenant(configuration.getMultiTenancyMasterDatabase());
		}

		multitenancyContext.setTenant(tenant);

	}

}
