package org.demoiselle.jee7.example.store.sale.service;

/*
 * Demoiselle Framework
 *
 * License: GNU Lesser General Public License (LGPL), version 3 or later.
 * See the lgpl.txt file in the root directory or <https://www.gnu.org/licenses/lgpl.html>.
 */

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.swagger.jaxrs.config.BeanConfig;

@ApplicationPath("api")
public class AppREST extends Application {

	public AppREST() {

		// Configurações minimas do Swagger
		BeanConfig beanConfig = new BeanConfig();
		beanConfig.setVersion("1.0.0");
		beanConfig.setBasePath("/venda/api");
		beanConfig.setResourcePackage("org.demoiselle.store.venda.service");
		beanConfig.setScan(true);
	}
}
