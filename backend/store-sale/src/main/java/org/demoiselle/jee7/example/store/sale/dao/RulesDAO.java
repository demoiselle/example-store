package org.demoiselle.jee7.example.store.sale.dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.demoiselle.jee.core.exception.DemoiselleException;

import org.demoiselle.jee7.example.store.sale.dao.EntityManager.EntityManagerDAO;
import org.demoiselle.jee7.example.store.sale.entity.Rules;

public class RulesDAO extends EntityManagerDAO<Rules> {
		
	public Rules findByName(String name) throws NoResultException {
		try {	        	
	        	 Query query = em.createQuery("SELECT i FROM Rules i where i.name=:name", Rules.class);
	        	 query.setParameter("name", name);
				 
	        	 return (Rules) query.getSingleResult();	        			    			
		         	            	            
			} catch (Exception e) {
			    logger.severe(e.getMessage());
			    throw new DemoiselleException("Não foi possível consultar", e.getCause());
			}	
	}	

}