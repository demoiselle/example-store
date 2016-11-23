/*
 * Demoiselle Framework
 *
 * License: GNU Lesser General Public License (LGPL), version 3 or later.
 * See the lgpl.txt file in the root directory or <https://www.gnu.org/licenses/lgpl.html>.
 */
package org.demoiselle.jee7.example.store.user.dao;

import javax.inject.Inject;

import org.demoiselle.jee7.example.store.user.dao.context.PersistenceContextMasterDAO;
import org.demoiselle.jee7.example.store.user.dao.multitenancy.MultiTenantContext;
import org.demoiselle.jee7.example.store.user.entity.Tenant;

public class TenantDAO extends PersistenceContextMasterDAO<Tenant> {

	@Inject
	private MultiTenantContext multiTenantContext;

	/**
	 * O Contrutor desta classe precisa ser sem parâmetros por causa do CDI.
	 */
	public TenantDAO() {
		super(Tenant.class);
	}

	public MultiTenantContext getMultiTenantContext() {
		return multiTenantContext;
	}

}
