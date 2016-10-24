package org.demoiselle.store.usuario.dao.multitenancy;

import javax.enterprise.context.RequestScoped;

import org.demoiselle.store.usuario.entity.Tenant;

@RequestScoped
public class MultiTenantContext {

	private Tenant tenant;

	public MultiTenantContext() {

	}

	public Tenant getTenant() {
		return tenant;
	}

	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}

}
