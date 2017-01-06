/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.demoiselle.jee7.example.store.sale.service;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.swagger.jaxrs.config.BeanConfig;

@ApplicationPath("api/v1")
public class ApplicationConfig extends Application {

    public ApplicationConfig() {
	    BeanConfig beanConfig = new BeanConfig();
	    beanConfig.setVersion("1.0.0");
	    beanConfig.setBasePath("/sales/api/v1");
	    beanConfig.setResourcePackage("org.demoiselle.jee7.example.store.sale.service");
	    beanConfig.setScan(true);
    }
}
