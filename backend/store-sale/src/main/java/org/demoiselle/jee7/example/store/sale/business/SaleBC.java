package org.demoiselle.jee7.example.store.sale.business;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import javax.script.ScriptException;
import javax.script.SimpleBindings;

import org.demoiselle.jee.core.api.security.SecurityContext;
import org.demoiselle.jee.core.api.security.Token;
import org.demoiselle.jee.persistence.crud.AbstractBusiness;
import org.demoiselle.jee.script.DynamicManager;
import org.demoiselle.jee7.example.store.sale.dao.RulesDAO;
import org.demoiselle.jee7.example.store.sale.entity.Cart;
import org.demoiselle.jee7.example.store.sale.entity.ItemCart;
import org.demoiselle.jee7.example.store.sale.entity.Itens;
import org.demoiselle.jee7.example.store.sale.entity.Product;
import org.demoiselle.jee7.example.store.sale.entity.Rules;
import org.demoiselle.jee7.example.store.sale.entity.Sale;
import org.demoiselle.jee7.example.store.sale.security.Credentials;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import java.util.logging.Logger;

public class SaleBC extends AbstractBusiness<Sale,Long>{

	 @Inject
	 private DynamicManager dm;
	 
	 @Inject
	 private SecurityContext securityContext;
	 
	 @Inject
	 private Token token;
	
	 @Inject
	 private RulesDAO rulesDAO;
	 	 
	 @Inject 
	 private Logger logger;
	 	
	 /**
	  * Sale preview.
	  * 
	  */
	 public Cart salePreview(Cart cart) throws ScriptException {	
		return  processaCarrinhoCupom(preProcessaCarrinho(cart));
	 }
	 
	 /**
	  * Complete the sale.
	  * 
	  */		 
	 public Cart saleComplete(Cart cart) throws ScriptException {
		Cart temp_cart = processaCarrinhoCupom( preProcessaCarrinho(cart));
		
		if(temp_cart != null) {
			Sale newSale = posProcessaCarrinho(temp_cart);
			
			newSale.setDatavenda(new Date());
			newSale.setUsuarioId(BigInteger.valueOf(1L));
			
			this.dao.persist(newSale);
		}
		return  temp_cart;			 			
	 }
	 
	 /**
	  * Validate the cupom
	  * 
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
	  * Cart pre-sale-confirm processing.
	  * 
	  */		 
	 public Cart preProcessaCarrinho (Cart cart) throws ScriptException {     	            
        for(ItemCart item : cart.getItens()){
        	
        	Product p = getProduct(item.getCodigoProduto());
    	    item.setNomeProduto(p.getDescricao());
    	    item.setValor(BigDecimal.valueOf(p.getValor()));  		
        }	        		        	        	        	       
        return cart;
	 }
	 
	 /**
	  * Cart pos-sale-confirm processing.
	  * 
	  */	  	 
	 public Sale posProcessaCarrinho (Cart cart) throws ScriptException {     	            
         Sale  sale = new Sale();
         List<Itens> listaItens = new ArrayList<Itens>();
         
		 for(ItemCart item : cart.getItens()){
        	
			Itens newitem = new Itens();
			newitem.setId(null);
			newitem.setValor(item.getValorComDesconto().floatValue());
			newitem.setProduto_id(item.getCodigoProduto());
			
			listaItens.add( newitem);
			
        	Product p = getProduct(item.getCodigoProduto());
        	
        	Integer total= p.getQuantidade();
        	if(total >= item.getQuantidade()){
        		p.setQuantidade(total - item.getQuantidade());
        		
        		String key = null;
        		if(!securityContext.isLoggedIn()){
        			key= doLogin("string","string");
        		}else
        		     key = token.getKey();
        		
        		putProduct(p,key);
        	}
        	 	
        }
		 		
        return sale;
	 }
	 
	 /**
	  * Return the token to perform a service access in other server.
	  * 
	  */
	 public String doLogin(String username,String password) {	 		 		 
					 		 
		Client client = Client.create();
		Gson gson = new Gson();
		Credentials login = new Credentials();
		login.setUsername(username);
		login.setPassword(password);
		
		WebResource webResource = client.resource("http://localhost:8080/user/api/auth/login");	
		
		ClientResponse response = (ClientResponse) webResource.accept("application/json").type("application/json").post(ClientResponse.class,gson.toJson(login));

		if (response.getStatus() != 200) {
		   throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		}

		String resposta = response.getEntity(String.class);		
		
		JsonParser parser = new JsonParser();
		JsonObject json = parser.parse(resposta).getAsJsonObject();
					
		return json.get("token").getAsString();
				 			
	 }
	   
	 /**
	  * Get the Product List.
	  * 
	  */
	 public Product getProduct(Long id) {	 
		 
		Client client = Client.create();

		WebResource webResource = client.resource("http://localhost:8080/store-product/api/product/" + id.toString());				
		ClientResponse response = (ClientResponse) webResource.accept("application/json").get(ClientResponse.class);

		if (response.getStatus() != 200) {
		   throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		}

		Product produto = response.getEntity(Product.class);		
		
		return produto;
					 			
	 }
	 
	 /**
	  * Update the product in product service.
	  * 
	  */	  
	 public void putProduct(Product p, String token)  {	 		 		 
		 try {
			 		 
				Client client = Client.create();
				Gson gson = new Gson();
				String Authorization =  "token " + token;
			        
				String baseuri= "http://localhost:8080/store-product/api/product/" ;
				WebResource webResource = client.resource(baseuri);		
              
                System.out.println("Call " + baseuri );
				System.out.println("Authorization: " + Authorization);
				System.out.println("PUT :" +  gson.toJson(p));
		        
				ClientResponse response = (ClientResponse) webResource.accept("application/json").header("Authorization", Authorization).type("application/json").put(ClientResponse.class,gson.toJson(p));

				if (response.getStatus() != 200) {
				   throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
				}
				
			  } catch (Exception e) {
				e.printStackTrace();

			  }
			 			
	 }
	 
	 /**
	  * Process the cart with/without cupons.
	  * 
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