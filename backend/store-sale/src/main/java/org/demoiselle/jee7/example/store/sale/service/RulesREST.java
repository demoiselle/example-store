/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.demoiselle.jee7.example.store.sale.service;
import javax.ws.rs.Path;

import org.demoiselle.jee.core.api.crud.Result;
import org.demoiselle.jee.persistence.crud.AbstractREST;
import org.demoiselle.jee7.example.store.sale.entity.Rules;

import io.swagger.annotations.Api;

@Api("Rules")
@Path("rules")
public class RulesREST extends AbstractREST<Rules, Long> {

	@Override
	public Result find(String field, String order, int init, int qtde) {
		// TODO Auto-generated method stub
		return null;
	}
  
}

