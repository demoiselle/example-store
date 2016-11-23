/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.demoiselle.store.venda.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.demoiselle.jee.script.DynamicManager;
import org.demoiselle.store.venda.entity.Carrinho;
import org.demoiselle.store.venda.entity.ItemCarrinho;
import org.demoiselle.store.venda.entity.Regras;
import com.google.gson.Gson;

import io.swagger.annotations.Api;

import javax.transaction.Transactional;

/**
 * Recebe e processa as requisições relacionadas a venda.
 * 
 */
@Api("Venda")
@Path("carrinho")
@Consumes({MediaType.APPLICATION_JSON})
public class CarrinhoFacadeREST {

    @PersistenceContext(unitName = "vendaPU")
    private EntityManager em;  
    private DynamicManager dm;

    public CarrinhoFacadeREST() {        
        dm = new DynamicManager();
        dm.loadEngine("groovy");
    }

    @POST
    @Transactional
    @Path("processaCarrinho")
    @Produces({MediaType.APPLICATION_JSON})
    public Carrinho processaCarrinho(String objeto) throws ScriptException {

        Gson gson = new Gson();
        Carrinho carrinho = gson.fromJson(objeto, Carrinho.class);
        String scriptName = "regrasDesconto";

        
        if (dm.getScript(scriptName) == null) {
            //Le o script da base...
            Regras regra = em.find(Regras.class, 4L);  		//Regra ID
            em.refresh(regra);								//Para atualizar a regra            
            dm.loadScript(scriptName, regra.getScript());
        }

        //TODO: List<Produto> listaProdutos obtem do banco (para cada codigoDoProduto atribui nos itensCarrinho do carrinho passado o valor correto do banco)
        for (ItemCarrinho item : carrinho.getItens()) { 
        	
            SimpleBindings contexto = new SimpleBindings();               //Lista de objetos para disponibilizar para a logica do script                     
            contexto.put(item.getClass().getSimpleName(), item);          //disponibiliza a classe do item  ItemCarrinho pro script
            contexto.put(carrinho.getClass().getSimpleName(), carrinho);  //disponibiliza o carrinho inteiro para o script
            dm.eval(scriptName, contexto); 								  //roda o script
        }
        //Remove do cache ... para sempre buscar no banco e compilar ..util para testar... comente para compilar somente uma vez
        dm.removeScript(scriptName);

        return carrinho;
    }

    /* Exemplo de carrinho ...
     
     {
	  "itens": [
	    {
	      "codigoProduto": 1,
	      "quantidade": 1,
	      "valor": 100
	    },
	    {
	      "codigoProduto": 2,
	      "quantidade": 1,
	      "valor": 100
	    },
	    {
	      "codigoProduto": 3,
	      "quantidade": 1,
	      "valor": 100
	    },
	    {
	      "codigoProduto": 4,
	      "quantidade": 1,
	      "valor": 100
	    }
	  ]
	}          
     */
}