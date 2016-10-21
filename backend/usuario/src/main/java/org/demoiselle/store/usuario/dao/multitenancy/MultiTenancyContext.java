package org.demoiselle.store.usuario.dao.multitenancy;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class MultiTenancyContext {

	private String tenant;

	public MultiTenancyContext() {

	}

	public String getTenant() {
		return tenant;
	}

	public void setTenant(String tenant) {
		this.tenant = tenant;
	}

}
