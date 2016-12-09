package org.demoiselle.jee7.example.store.sale.dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.demoiselle.jee7.example.store.sale.dao.EntityManager.EntityManagerDAO;
import org.demoiselle.jee7.example.store.sale.entity.Rules;

public class RulesDAO extends EntityManagerDAO<Rules> {

	public Rules findByName(String name){
		try {
			Query query = em.createQuery("SELECT i FROM Rules i where i.name=:name", Rules.class);
			query.setParameter("name", name);

			return (Rules) query.getSingleResult();

		} catch ( NoResultException e ) {
			return null;
		}
	}

}