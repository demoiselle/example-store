package org.demoiselle.jee7.example.store.user.dao.multitenancy;

import javax.enterprise.context.RequestScoped;

import org.demoiselle.jee7.example.store.user.entity.Tenant;

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
