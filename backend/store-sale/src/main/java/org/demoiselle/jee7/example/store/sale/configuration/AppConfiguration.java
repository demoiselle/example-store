package org.demoiselle.jee7.example.store.sale.configuration;

import org.demoiselle.jee.configuration.annotation.Configuration;

@Configuration
public class AppConfiguration {
	
	private String productApiUrl;
	private String userApiUrl;
	private String adminUser;
	private String adminPasswd;
	
	public String getProductApiUrl() {
		return productApiUrl;
	}
	public void setProductApiUrl(String productApiUrl) {
		this.productApiUrl = productApiUrl;
	}
	public String getUserApiUrl() {
		return userApiUrl;
	}
	public void setUserApiUrl(String userApiUrl) {
		this.userApiUrl = userApiUrl;
	}
	public String getAdminUser() {
		return adminUser;
	}
	public void setAdminUser(String adminUser) {
		this.adminUser = adminUser;
	}
	public String getAdminPasswd() {
		return adminPasswd;
	}
	public void setAdminPasswd(String adminPasswd) {
		this.adminPasswd = adminPasswd;
	}

	
}