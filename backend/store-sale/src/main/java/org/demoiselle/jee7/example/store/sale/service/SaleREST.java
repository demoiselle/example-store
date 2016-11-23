/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.demoiselle.jee7.example.store.sale.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.demoiselle.jee.script.DynamicManager;
import org.demoiselle.jee7.example.store.sale.entity.Cart;
import org.demoiselle.jee7.example.store.sale.entity.ItemCart;
import org.demoiselle.jee7.example.store.sale.entity.Rule;

import com.google.gson.Gson;

import io.swagger.annotations.Api;

/**
 * Recebe e processa as requisições relacionadas a venda.
 * 
 */
@Api("Venda")
@Path("cart")
@Consumes({MediaType.APPLICATION_JSON})
public class SaleREST {

    @PersistenceContext(unitName = "vendaPU")
    private EntityManager em;  
    private DynamicManager dm;

    public SaleREST() {        
        dm = new DynamicManager();
        dm.loadEngine("groovy");
    }

    @POST
    @Transactional
    @Path("processaCart")
    @Produces({MediaType.APPLICATION_JSON})
    public Cart processaCart(String objeto) throws ScriptException {

        Gson gson = new Gson();
        Cart carrinho = gson.fromJson(objeto, Cart.class);
        String scriptName = "regrasDesconto";

        
        if (dm.getScript(scriptName) == null) {
            //Le o script da base...
            Rule regra = em.find(Rule.class, 4L);  		//Regra ID
            em.refresh(regra);								//Para atualizar a regra            
            dm.loadScript(scriptName, regra.getScript());
        }	

        //TODO: List<Produto> listaProdutos obtem do banco (para cada codigoDoProduto atribui nos itensCart do carrinho passado o valor correto do banco)
        for (ItemCart item : carrinho.getItens()) { 
        	
            SimpleBindings contexto = new SimpleBindings();               //Lista de objetos para disponibilizar para a logica do script                     
            contexto.put(item.getClass().getSimpleName(), item);          //disponibiliza a classe do item  ItemCart pro script
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