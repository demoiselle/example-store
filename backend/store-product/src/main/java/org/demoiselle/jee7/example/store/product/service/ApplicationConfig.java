package org.demoiselle.jee7.example.store.product.service;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.swagger.jaxrs.config.BeanConfig;

@ApplicationPath("api/v1")
public class ApplicationConfig extends Application {

        public ApplicationConfig() {
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.8.0");
        beanConfig.setBasePath("/products/api/v1");
        beanConfig.setResourcePackage("org.demoiselle.jee7.example.store.product.service");
        beanConfig.setScan(true);
    }
}
