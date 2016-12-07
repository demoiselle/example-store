package org.demoiselle.jee7.example.store.sale.dao;

import java.util.List;

import org.demoiselle.jee7.example.store.sale.dao.EntityManager.EntityManagerDAO;
import org.demoiselle.jee7.example.store.sale.entity.Itens;

public class ItensDAO extends EntityManagerDAO<Itens> {

	public List<Itens> getAllItens(Long vendaId) {
        try {
            return (List<Itens> )em.createQuery("select i from Itens i where i.venda.id =" + vendaId).getResultList();
        } catch (Throwable th) {
            th.printStackTrace();
        }
		return null;
    }
	
		

}
