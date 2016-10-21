package org.demoiselle.store.usuario.dao.multitenancy;

import javax.enterprise.inject.spi.CDI;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

public class SchemaResolver implements CurrentTenantIdentifierResolver {

	@Override
	public String resolveCurrentTenantIdentifier() {
		MultiTenancyContext o = CDI.current().select(MultiTenancyContext.class).get();
		return o.getTenant();
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		return false;
	}

}