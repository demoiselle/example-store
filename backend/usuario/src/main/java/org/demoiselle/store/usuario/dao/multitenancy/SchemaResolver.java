package org.demoiselle.store.usuario.dao.multitenancy;

import javax.enterprise.inject.spi.CDI;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

public class SchemaResolver implements CurrentTenantIdentifierResolver {

	@Override
	public String resolveCurrentTenantIdentifier() {
		MultiTenantContext o = CDI.current().select(MultiTenantContext.class).get();
		return o.getTenant().getName();
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		return false;
	}

}