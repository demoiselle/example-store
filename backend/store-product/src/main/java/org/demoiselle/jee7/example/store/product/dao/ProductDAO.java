/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.demoiselle.jee7.example.store.product.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.demoiselle.jee.persistence.crud.AbstractDAO;
import org.demoiselle.jee7.example.store.product.entity.Product;

/**
 *
 * @author 70744416353
 */
public class ProductDAO extends AbstractDAO<Product,Long>{

	@PersistenceContext(unitName = "productPU")
	private EntityManager em;  
	
	@Override
	protected EntityManager getEntityManager() {
	
		return em;
	}
    
}
