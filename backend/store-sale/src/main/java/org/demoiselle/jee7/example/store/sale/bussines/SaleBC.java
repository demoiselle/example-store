package org.demoiselle.jee7.example.store.sale.bussines;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

import org.demoiselle.jee.script.DynamicManager;
import org.demoiselle.jee7.example.store.sale.entity.Cart;
import org.demoiselle.jee7.example.store.sale.entity.ItemCart;
import org.demoiselle.jee7.example.store.sale.entity.Rules;
import org.demoiselle.jee7.example.store.sale.entity.Sale;

import java.util.logging.Logger;

@RequestScoped
public class SaleBC {

	 @Inject
	 private DynamicManager dm;
	 
	 @Inject 
	 private Logger logger;
	 
	 @PersistenceContext(unitName = "salePU")
	 private EntityManager em;  
	 	 
	 /**
	  * Preview the sale.
	  * 
	  * @param cart
	  * @return
	  * @throws ScriptException
	  */
	 public Cart salePreview(Cart cart) throws ScriptException {		 
		processaCarrinhoCupom(cart);
		 
		return cart;
	 }
	 
	 /**
	  * Complete the sale.
	  * 
	  * @param cart
	  * @return
	  * @throws ScriptException
	  */
	 public Cart saleComplete(Cart cart) throws ScriptException {
		
		Cart temp_cart = processaCarrinhoCupom( cart );
		if(temp_cart != null) {
			Sale newSale = new Sale();
			
			newSale.setDatavenda(new Date());
			newSale.setUsuarioId(BigInteger.valueOf(1L));
			
			em.persist(newSale);
		}
		return  temp_cart;			 			
	 }
	 
	 
	 /**
	  * Validate the cupom.
	  * 
	  * @param cart
	  * @return
	  * @throws ScriptException
	  */
	 public Rules validateCupom(String cupom) {		
		 try 
		 { 		    		
			 Query query = em.createQuery("SELECT i FROM Rules i where i.name=:name", Rules.class);
			 query.setParameter("name", cupom);
 					 
 			 Rules rule = (Rules) query.getSingleResult();	        			    			
 			 em.refresh(rule);
 			 
 			 Date data = new Date();
		    
		     if(data.before(rule.getStartDate()) || data.after(rule.getStopDate())){
		    	logger.warning("Cupom \"" + cupom + "\" expired.");
		     }
		     else{
		    	 logger.info("Cupom \"" + cupom + "\" validated.");       	 			 
		    	 return rule;
		     }
 		    
		 }catch(NoResultException e){	        			
 		    	logger.warning("Cupom \"" + cupom + "\" not valid!"); 		    	
   		}	 	
		 
		return null;
	 }	 

	 /**
	  * Process the cart.
	  * 
	  * @param cart
	  * @return
	  * @throws ScriptException
	  */
	 public Cart processaCarrinhoCupom (Cart cart) throws ScriptException {
	        /*
	        Regras regra2 = new Regras();
	        regra2.setName("desconto10");
		    regra2.setScript("if (ItemCarrinho.codigoProduto==1) { ItemCarrinho.addDesconto(\"desconto10\",10,false); }");
		    regra2.setSistemaId(1L);
		    
		    Regras regra3 = new Regras();
		    regra3.setName("desconto20");
		    regra3.setScript("if (ItemCarrinho.codigoProduto==1) { ItemCarrinho.addDesconto(\"p2-desconto20\",20); }");   
		    regra3.setSistemaId(1L);
		   		   
		    em.persist(regra2); 
		    em.persist(regra3); 
		    */     
	       	            
	        for(String cupom : cart.getListaCupons()){		        	
	        	dm.loadEngine("groovy");
	        	
	        	if (dm.getScript(cupom) == null) {
	        		Rules rule = validateCupom(cupom);
	        		    
	        		if( rule != null) {	        			
   	        		    dm.loadScript(cupom, rule.getScript());	        		 
	            			            		
	            		for(ItemCart item : cart.getItens()){
	    		        	SimpleBindings context = new SimpleBindings();                                   
	    		        	context.put(item.getClass().getSimpleName(),item);
	    		        	context.put(cart.getClass().getSimpleName(),cart);           
	    		        	dm.eval(cupom, context); 						   //run the script of rule
	    	        	}
	    	        	//Remove from cache for tests....
	    	        	dm.removeScript(cupom);	        				 
        		    }
	        	}	        		       			        		
	        }	        		        	        	        	       
	        return cart;
	 }
	 
	 private Class<Sale> entityClass =  Sale.class;

		private EntityManager getEntityManager(){
		   return em; 
		}

		public void create(Sale entity) {
		    getEntityManager().persist(entity);
		}
		
		public void edit(Sale entity) {
		    getEntityManager().merge(entity);
		}
		
		public void remove(Sale entity) {
		    getEntityManager().remove(getEntityManager().merge(entity));
		}
		
		public Sale find(Object id) {
		    return getEntityManager().find(entityClass, id);
		}
		
		public List<Sale> findAll() {
		    javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
		    cq.select(cq.from(entityClass));
		    return getEntityManager().createQuery(cq).getResultList();
		}
		
		public List<Sale> findRange(int[] range) {
		    javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
		    cq.select(cq.from(entityClass));
		    javax.persistence.Query q = getEntityManager().createQuery(cq);
		    q.setMaxResults(range[1] - range[0]);
		    q.setFirstResult(range[0]);
		    return q.getResultList();
		}
		
		public int count() {
		    javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
		    javax.persistence.criteria.Root<Sale> rt = cq.from(entityClass);
		    cq.select(getEntityManager().getCriteriaBuilder().count(rt));
		    javax.persistence.Query q = getEntityManager().createQuery(cq);
		    return ((Long) q.getSingleResult()).intValue();
		}
}