package org.demoiselle.jee7.example.store.sale.business;

import javax.inject.Inject;
import javax.script.ScriptException;

import org.demoiselle.jee.crud.AbstractBusiness;
import org.demoiselle.jee.rest.exception.DemoiselleRestException;
import org.demoiselle.jee.script.DynamicManager;
import org.demoiselle.jee7.example.store.sale.dao.RulesDAO;
import org.demoiselle.jee7.example.store.sale.entity.Rules;
import org.demoiselle.tenant.hibernate.business.TenantManager;

public class RulesBC extends AbstractBusiness<Rules, Long> {

	@Inject 
	private DynamicManager dm;
	
	@Inject 
	private RulesDAO rulesDAO;
	
	@Inject
	private TenantManager tenantManager;
	
	private String engine = "groovy";
		
	public Rules addRule(Rules regra) throws ScriptException {
		String engineName = engine;
		
		Rules rule = rulesDAO.findByName(regra.getName());
		
		if( rule == null ){
			if( dm.compile(engineName, regra.getScript()) != null){
				Rules newRegra = new Rules();
				
				newRegra.setId(null);
				newRegra.setName(regra.getName());
				newRegra.setSistemaId(regra.getSistemaId());
				newRegra.setStartDate(regra.getStartDate());
				newRegra.setStopDate(regra.getStopDate());
				newRegra.setScript(regra.getScript());
				
				this.dao.persist(newRegra);		
				
				return newRegra;
			}
		}else{
			throw new DemoiselleRestException("Rule already exist.");
		}	
		
		return null;
	}

	public Rules updateRule(Rules upRule) throws ScriptException {				
		Rules rule = dao.find(upRule.getId());
		String ruleName = tenantManager.getTenantName() + "_" + upRule.getName();
	
		this.dao.merge(upRule);
		
		dm.loadEngine(engine);
		if(dm.getScript(engine, rule.getName()) != null ){
			dm.updateScript(engine, ruleName, upRule.getScript());
		}else {
			dm.loadScript(engine, ruleName, upRule.getScript());
		}
		
		return upRule;
				
	}
	 	 	
}
