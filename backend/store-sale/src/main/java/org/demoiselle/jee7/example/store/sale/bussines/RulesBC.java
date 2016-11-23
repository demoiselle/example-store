package org.demoiselle.jee7.example.store.sale.bussines;

import java.util.List;
import javax.enterprise.context.RequestScoped;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.demoiselle.jee7.example.store.sale.entity.Rules;


@RequestScoped
public class RulesBC { 
 	 	 
	@PersistenceContext(unitName = "salePU")
	private EntityManager em;  
	  
	private Class<Rules> entityClass =  Rules.class;

	private EntityManager getEntityManager(){
	   return em; 
	}

	public void create(Rules entity) {
	    getEntityManager().persist(entity);
	}
	
	public void edit(Rules entity) {
	    getEntityManager().merge(entity);
	}
	
	public void remove(Rules entity) {
	    getEntityManager().remove(getEntityManager().merge(entity));
	}
	
	public Rules find(Object id) {
	    return getEntityManager().find(entityClass, id);
	}
	
	public List<Rules> findAll() {
	    javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
	    cq.select(cq.from(entityClass));
	    return getEntityManager().createQuery(cq).getResultList();
	}
	
	public List<Rules> findRange(int[] range) {
	    javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
	    cq.select(cq.from(entityClass));
	    javax.persistence.Query q = getEntityManager().createQuery(cq);
	    q.setMaxResults(range[1] - range[0]);
	    q.setFirstResult(range[0]);
	    return q.getResultList();
	}
	
	public int count() {
	    javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
	    javax.persistence.criteria.Root<Rules> rt = cq.from(entityClass);
	    cq.select(getEntityManager().getCriteriaBuilder().count(rt));
	    javax.persistence.Query q = getEntityManager().createQuery(cq);
	    return ((Long) q.getSingleResult()).intValue();
	}
	
}
