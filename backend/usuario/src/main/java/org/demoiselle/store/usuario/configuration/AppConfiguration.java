package org.demoiselle.store.usuario.configuration;

import org.demoiselle.jee.configuration.ConfigType;
import org.demoiselle.jee.configuration.annotation.Configuration;
import org.demoiselle.jee.core.annotation.Name;

@Configuration(prefix = "demoiselle", type = ConfigType.PROPERTIES, resource = "demoiselle")
public class AppConfiguration {

	@Name(value = "appSearchUrl")
	private String appSearchUrl;

	public String getAppSearchUrl() {
		return appSearchUrl;
	}

}