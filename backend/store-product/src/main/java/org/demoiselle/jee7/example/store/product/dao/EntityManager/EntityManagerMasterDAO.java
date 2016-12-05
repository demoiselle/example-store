package org.demoiselle.jee7.example.store.product.dao.EntityManager;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.demoiselle.jee.multitenancy.hibernate.dao.context.EntityManagerMaster;

public class EntityManagerMasterDAO implements EntityManagerMaster {

	@PersistenceContext(unitName = "ProductMasterPU")
	protected EntityManager emEntity;

	public EntityManager getEntityManager() {
		return emEntity;
	}

}
