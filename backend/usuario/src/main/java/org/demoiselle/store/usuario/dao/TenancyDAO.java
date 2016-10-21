/*
 * Demoiselle Framework
 *
 * License: GNU Lesser General Public License (LGPL), version 3 or later.
 * See the lgpl.txt file in the root directory or <https://www.gnu.org/licenses/lgpl.html>.
 */
package org.demoiselle.store.usuario.dao;

import javax.inject.Inject;

import org.demoiselle.store.usuario.dao.context.PersistenceContextMasterDAO;
import org.demoiselle.store.usuario.dao.multitenancy.MultiTenancyContext;
import org.demoiselle.store.usuario.entity.Tenant;

public class TenancyDAO extends PersistenceContextMasterDAO<Tenant> {

	@Inject
	private MultiTenancyContext multitenancyContext;

	/**
	 * O Contrutor desta classe precisa ser sem parâmetros por causa do CDI.
	 */
	public TenancyDAO() {
		super(Tenant.class);
	}

	public String getMultitenancyContext() {
		return multitenancyContext.getTenant();
	}

}
