package org.demoiselle.jee7.entity;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.Map;

import javax.persistence.EntityManager;

import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.Script;
// cassiomaes
public class SimpleGroovyEngine {
	
	private Map<String,Script> scriptCache; //cache simples
	private GroovyClassLoader groovyClassLoader;	
	private EntityManager em; //se o em ficar dentro do engine
	private boolean usaCache;
	
	public SimpleGroovyEngine(){
		//inicializa ...
	    ClassLoader parent = getClass().getClassLoader();
	    groovyClassLoader  = new GroovyClassLoader(parent);			     
	    scriptCache = new HashMap<String,Script>();
	}
	
	public Map<String, Script> getScriptCache() {
		return scriptCache;
	}

	public void setScriptCache(Map<String, Script> scriptCache) {
		this.scriptCache = scriptCache;
	}

	public Script getScript(String scriptId ) {		 
		return scriptCache.get(scriptId);
		
	}
	
	public boolean isUsaCache() {
		return usaCache;
	}

	public void setUsaCache(boolean usaCache) {
		this.usaCache = usaCache;
	}

	@SuppressWarnings("unchecked")
	public Script loadScript(String scriptId , String regraScript ) {		 
		Script script = null;		
		try {			
			script= scriptCache.get(scriptId);
			if (script == null) {	
				
				/* Da para jogar o entity manager da regra pra dentro do engine pra buscar o script 
				 * do banco automaticamente se nao encontar..
				 */
				/*
				if( script) == null) {
		    		//Le o script da base...
					Regras regra= em.find(Regras.class, 4L);  //Regra ID
					em.refresh(regra);// pra atualizar a regra
					Class<Script> clazz= groovyClassLoader.parseClass(regra.getScript());									  
					script = clazz.newInstance();					       		    
			    	scriptCache.put(scriptId, script);																												
				}		
				/*/
															
				Class<Script> clazz= groovyClassLoader.parseClass(regraScript);									  
				script = clazz.newInstance();
					       		    
			    scriptCache.put(scriptId, script);		 
			}	   	   	       	    	   	
			 
		 } catch (InstantiationException | IllegalAccessException e) {				
				e.printStackTrace();
		 }
		return script;
		    
	}

	public void run(String scriptId, ArrayList<Object> listaFatos ) {		
		Script script = scriptCache.get(scriptId);
		
		if( script != null ){			   	   	       	    	 
			Binding binding = new Binding();
			
			for(Object item : listaFatos ) {
				binding.setVariable(item.getClass().getSimpleName(), item);							
			} 
			
			script.setBinding(binding);
			script.run();			
		}
		    
	}
	
	//da pra passar o binding pronto tb..
	public void run(String scriptId, Binding listaFatos ) {		
		Script script = scriptCache.get(scriptId);
		
		if( script != null ){			   	   	       	    	 			
			script.setBinding(listaFatos);
			script.run();			
		}
		    
	}
	
	public void removeScriptCache(String scriptId) {					
		this.scriptCache.remove(scriptId); 		
	}  
	
}