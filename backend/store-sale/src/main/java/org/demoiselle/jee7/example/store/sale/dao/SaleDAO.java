package org.demoiselle.jee7.example.store.sale.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.demoiselle.jee7.example.store.sale.dao.EntityManager.EntityManagerDAO;
import org.demoiselle.jee7.example.store.sale.entity.Sale;

public class SaleDAO extends EntityManagerDAO<Sale> {

	@SuppressWarnings("unchecked")
	public List<Sale> listUserSales(String usuarioId) {
		try {

			Query query = em.createQuery("SELECT s FROM Sale s where s.usuarioId=:usuarioId", Sale.class);
			query.setParameter("usuarioId", usuarioId);

			List<Sale> resultList = query.getResultList();
			return resultList;

		} catch (NoResultException e) {
			return null;
		}
	}

}
