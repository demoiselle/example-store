package org.demoiselle.store.usuario.configuration;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.deltaspike.core.api.config.ConfigProperty;
import org.apache.deltaspike.core.api.projectstage.ProjectStage;

@ApplicationScoped
public class AppConfiguration {

	@Inject
	private ProjectStage projectStage;

	@Inject
	@ConfigProperty(name = "url", defaultValue = "NADA!")
	private String appSearchUrl;

	public String appSearchUrl() {
		System.out.println(this.projectStage);		
		return this.appSearchUrl;
	}

}