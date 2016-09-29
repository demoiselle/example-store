/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.demoiselle.jee7.service;

import java.util.ArrayList;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.google.gson.Gson;
import javax.transaction.Transactional;

import org.demoiselle.jee.script.SimpleGroovyEngine;
import org.demoiselle.jee7.cover.Carrinho;
import org.demoiselle.jee7.cover.ItemCarrinho;
import org.demoiselle.jee7.entity.Regras;

/**
 *
 * @author cassiomaes
 */
@Path("carrinho")
@Consumes({MediaType.APPLICATION_JSON})
public class CarrinhoFacadeREST {

    @PersistenceContext(unitName = "vendaPU")
    private EntityManager em;
    private SimpleGroovyEngine engine;

    public CarrinhoFacadeREST() {
        engine = new SimpleGroovyEngine();
    }

    @POST
    @Transactional
    @Path("processaCarrinho")
    @Produces({MediaType.APPLICATION_JSON})
    public Carrinho processaCarrinho(String objeto) {

        Gson gson = new Gson();
        Carrinho carrinho = gson.fromJson(objeto, Carrinho.class);

        String scriptName = "regrasDesconto";

        //esse teste pode ser feito dentro do engine no metodo loadScript ... porem ficaria atrelado ao objeto Regras
        if (engine.getScript(scriptName) == null) {
            //Le o script da base...
            Regras regra = em.find(Regras.class, 4L);  //Regra ID
            em.refresh(regra);// pra atualizar a regra
            engine.loadScript(scriptName, regra.getScript());

        }

        //TODO: List<Produto> listaProdutos obtem do banco; 
        //para cada codigoDoProduto atribui nos itensCarrinho do carrinho passado o valor correto do banco
        for (ItemCarrinho item : carrinho.getItens()) {
            //Lista de objetos para disponibilizar para a logica do script
            ArrayList<Object> listaFatos = new ArrayList<Object>();

            listaFatos.add(item);  //disponibiliza a classe do item  ItemCarrinho pro script
            listaFatos.add(carrinho); //disponibiliza o carrinho inteiro para o script

            //roda o script
            engine.run(scriptName, listaFatos);
        }
        //remove do cache ... para sempre buscar no banco e compilar ..util para testar... comente para compilar somente uma vez
        engine.removeScriptCache(scriptName);

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
