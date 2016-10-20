package org.demoiselle.store.usuario.dao.multitenancy;

import javax.enterprise.context.RequestScoped;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

@RequestScoped
public class SchemaResolver implements CurrentTenantIdentifierResolver {

	private String tenantIdentifier;

	@Override
	public String resolveCurrentTenantIdentifier() {
		return tenantIdentifier;
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		return false;
	}

	public void setTenantIdentifier(String tenantIdentifier) {
		this.tenantIdentifier = tenantIdentifier;
	}
}