package org.demoiselle.jee7.example.store.sale.business;

import java.math.BigInteger;
import java.util.Date;

import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;

import javax.script.ScriptException;
import javax.script.SimpleBindings;

import org.demoiselle.jee.persistence.crud.AbstractBusiness;
import org.demoiselle.jee.script.DynamicManager;
import org.demoiselle.jee7.example.store.sale.dao.RulesDAO;
import org.demoiselle.jee7.example.store.sale.entity.Cart;
import org.demoiselle.jee7.example.store.sale.entity.ItemCart;
import org.demoiselle.jee7.example.store.sale.entity.Rules;
import org.demoiselle.jee7.example.store.sale.entity.Sale;

import java.util.logging.Logger;

public class SaleBC extends AbstractBusiness<Sale,Long>{

	 @Inject
	 private DynamicManager dm;
	 
	 @Inject
	 private RulesDAO rulesDAO;
	 	 
	 
	 @Inject 
	 private Logger logger;
	 	 	 
	 /**
	  * Preview the sale.
	  * 
	  * @param cart
	  * @return
	  * @throws ScriptException
	  */
	 public Cart salePreview(Cart cart) throws ScriptException {		 		 
		return  processaCarrinhoCupom(cart);
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
			
			this.dao.persist(newSale);
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
			 Rules rule = rulesDAO.findByName(cupom); 			 
 		
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
	 
}