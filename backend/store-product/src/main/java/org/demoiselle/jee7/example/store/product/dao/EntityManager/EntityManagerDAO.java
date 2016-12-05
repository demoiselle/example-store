package org.demoiselle.jee7.example.store.product.dao.EntityManager;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.demoiselle.jee.persistence.crud.AbstractDAO;

public abstract class EntityManagerDAO<T> extends AbstractDAO<T, Long> {

	@PersistenceContext(unitName = "ProductTenantsPU")
	protected EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

}


