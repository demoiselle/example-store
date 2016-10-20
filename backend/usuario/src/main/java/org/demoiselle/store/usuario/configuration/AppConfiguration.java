package org.demoiselle.store.usuario.configuration;

import org.demoiselle.jee.configuration.annotation.Configuration;

@Configuration
public class AppConfiguration {

	private String appSearchUrl;

	private String multitenacySetDatabaseSQL;

	private String multitenacyCreateDatabaseSQL;

	private String multitenacyDropDatabaseSQL;

	private String multitenacyTenantsDatabaseDatasource;

	private String multitenacyMasterDatabaseDatasource;

	private String multitenacyCreateDatabaseDDL;

	private String multitenacyDropDatabaseDDL;

	public String getAppSearchUrl() {
		return appSearchUrl;
	}

	public String getMultitenacySetDatabaseSQL() {
		return multitenacySetDatabaseSQL;
	}

	public String getMultitenacyCreateDatabaseSQL() {
		return multitenacyCreateDatabaseSQL;
	}

	public String getMultitenacyDropDatabaseSQL() {
		return multitenacyDropDatabaseSQL;
	}

	public String getMultitenacyTenantsDatabaseDatasource() {
		return multitenacyTenantsDatabaseDatasource;
	}

	public String getMultitenacyMasterDatabaseDatasource() {
		return multitenacyMasterDatabaseDatasource;
	}

	public String getMultitenacyCreateDatabaseDDL() {
		return multitenacyCreateDatabaseDDL;
	}

	public String getMultitenacyDropDatabaseDDL() {
		return multitenacyDropDatabaseDDL;
	}

}