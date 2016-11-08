package org.demoiselle.store.usuario.configuration;

import org.demoiselle.jee.configuration.annotation.Configuration;

@Configuration
public class AppConfiguration {

	private String appSearchUrl;

	private String multiTenancySetDatabaseSQL;

	private String multiTenancyCreateDatabaseSQL;

	private String multiTenancyDropDatabaseSQL;

	private String multiTenancyTenantsDatabaseDatasource;

	private String multiTenancyMasterDatabaseDatasource;

	private String multiTenancyCreateDatabaseDDL;

	private String multiTenancyDropDatabaseDDL;

	private String multiTenancyMasterDatabase;

	public String getAppSearchUrl() {
		return appSearchUrl;
	}

	public String getMultiTenancySetDatabaseSQL() {
		return multiTenancySetDatabaseSQL;
	}

	public String getMultiTenancyCreateDatabaseSQL() {
		return multiTenancyCreateDatabaseSQL;
	}

	public String getMultiTenancyDropDatabaseSQL() {
		return multiTenancyDropDatabaseSQL;
	}

	public String getMultiTenancyTenantsDatabaseDatasource() {
		return multiTenancyTenantsDatabaseDatasource;
	}

	public String getMultiTenancyMasterDatabaseDatasource() {
		return multiTenancyMasterDatabaseDatasource;
	}

	public String getMultiTenancyCreateDatabaseDDL() {
		return multiTenancyCreateDatabaseDDL;
	}

	public String getMultiTenancyDropDatabaseDDL() {
		return multiTenancyDropDatabaseDDL;
	}

	public String getMultiTenancyMasterDatabase() {
		return multiTenancyMasterDatabase;
	}

}