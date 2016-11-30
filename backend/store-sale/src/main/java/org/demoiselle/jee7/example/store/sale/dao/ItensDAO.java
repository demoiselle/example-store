package org.demoiselle.jee7.example.store.sale.dao;

import java.util.List;

import org.demoiselle.jee7.example.store.sale.dao.EntityManager.EntityManagerDAO;
import org.demoiselle.jee7.example.store.sale.entity.Itens;

public class ItensDAO extends EntityManagerDAO<Itens> {

	public List<Itens> getAllItens(Long vendaId) {
        try {
            return em.createQuery("select * from itens where venda_id =" + vendaId).getResultList();
        } catch (Throwable th) {
            // Throw Exception
        }
		return null;
    }
	
		

}
