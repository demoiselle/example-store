/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.demoiselle.jee7.example.store.sale.service;
import javax.inject.Inject;
import javax.script.ScriptException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.demoiselle.jee.core.api.crud.Result;
import org.demoiselle.jee.crud.AbstractREST;
import org.demoiselle.jee.rest.exception.DemoiselleRestException;
import org.demoiselle.jee.security.annotation.RequiredRole;
import org.demoiselle.jee7.example.store.sale.business.RulesBC;
import org.demoiselle.jee7.example.store.sale.entity.Rules;

import io.swagger.annotations.Api;

@Api("Rules")
@Path("rules")
public class RulesREST extends AbstractREST<Rules, Long> {
 
	@Inject
	private RulesBC rulesBC;
		
	@Override
	@POST
	//@RequiredRole("ADMINISTRATOR")
	@Transactional
	public Rules persist(@Valid Rules rule) {		
	    Rules regra =null;			
	    try {
			regra= rulesBC.addRule(rule);
		} catch (ScriptException e) {
			throw new DemoiselleRestException("Insert Rule error");
		}
		return regra;		
	}
	
	@Override
	@DELETE
	@Path("{id}")
	@Transactional
	//@RequiredRole("ADMINISTRATOR")
	public void remove(@PathParam("id") Long id) {
		bc.remove(id);
	}
	
	@Override
	@GET
	@Path("{id}")
	@Transactional
	//@RequiredRole("ADMINISTRATOR")
	public Rules find(@PathParam("id") Long id) {
		return bc.find(id);
	}
	
	@Override
	@GET
	@Transactional
	//@RequiredRole("ADMINISTRATOR")
	public Result find() {
		return bc.find();
	}	
	
	@Override
	@PUT
	@Transactional
	//@RequiredRole("ADMINISTRATOR")
	public Rules merge (@Valid Rules rule) {						
		try {
			return rulesBC.updateRule(rule);
			
		} catch (ScriptException e) {
			throw new DemoiselleRestException("Update Rule error");
		}		
		
	}
	
}