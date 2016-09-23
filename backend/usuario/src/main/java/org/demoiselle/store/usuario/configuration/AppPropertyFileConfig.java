package org.demoiselle.store.usuario.configuration;

import org.apache.deltaspike.core.api.config.PropertyFileConfig;

public class AppPropertyFileConfig implements PropertyFileConfig {

	@Override
	public String getPropertyFileName() {
		return "application.properties";
	}

	@Override
	public boolean isOptional() {
		return false;
	}

}