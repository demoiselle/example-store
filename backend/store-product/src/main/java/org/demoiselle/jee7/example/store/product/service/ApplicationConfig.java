package org.demoiselle.jee7.example.store.product.service;

import io.swagger.jaxrs.config.BeanConfig;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("api")
public class ApplicationConfig extends Application {

        public ApplicationConfig() {
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.8.0");
        beanConfig.setBasePath("/store-product/api");
        beanConfig.setResourcePackage("org.demoiselle.jee7.example.store.product.service");
        beanConfig.setScan(true);
    }
}
