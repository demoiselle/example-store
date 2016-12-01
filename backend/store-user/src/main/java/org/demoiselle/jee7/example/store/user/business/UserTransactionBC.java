/*
 * Demoiselle Framework
 *
 * License: GNU Lesser General Public License (LGPL), version 3 or later.
 * See the lgpl.txt file in the root directory or <https://www.gnu.org/licenses/lgpl.html>.
 */
package org.demoiselle.jee7.example.store.user.business;

import java.util.UUID;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;

import org.demoiselle.jee7.example.store.user.dao.UserDAO;
import org.demoiselle.jee7.example.store.user.entity.User;

/**
 * O TransactionManagementType.BEAN significa que o ciclo de transação das
 * requisições será gerenciado por padrão pelo usuário utilizando o objeto
 * UserTransaction. Neste caso quando se quiser que o container trate as
 * requisições (begin, commit e rollback) basta anotar o método
 * com @Transaction.
 * 
 * @author SERPRO
 *
 */
@Stateless
@TransactionManagement(value = TransactionManagementType.BEAN)
public class UserTransactionBC {

	@Inject
	private UserDAO dao;

	@Resource
	private UserTransaction userTransaction;

	public User persist(User entity) {
		return dao.persist(entity);
	}

	public void createTesteTransacional1(User usuario)
			throws SecurityException, IllegalStateException, RollbackException, HeuristicMixedException,
			HeuristicRollbackException, SystemException, NotSupportedException {

		userTransaction.begin();
		persist(usuario);
		userTransaction.commit();

		userTransaction.begin();

		UUID uuid = UUID.randomUUID();
		String emailUniqueTest = uuid.toString() + "@teste.com.br";
		String nomeUniqueTest = "Nome " + uuid.toString();

		usuario = new User();
		usuario.setId(null);
		usuario.setName(nomeUniqueTest);
		usuario.setEmail(emailUniqueTest);
		persist(usuario);

		usuario = new User();
		usuario.setId(null);
		usuario.setName(nomeUniqueTest);
		usuario.setEmail(emailUniqueTest);
		persist(usuario);

		userTransaction.commit();
	}

	public void createTesteTransacional2(User usuario) {
		persist(usuario);
	}

	@Transactional
	public void createTesteTransacional3(User usuario) {

		persist(usuario);

		UUID uuid = UUID.randomUUID();
		String emailUniqueTest = uuid.toString() + "@teste.com.br";
		String nomeUniqueTest = "Nome " + uuid.toString();

		usuario = new User();
		usuario.setId(null);
		usuario.setName(nomeUniqueTest);
		usuario.setEmail(emailUniqueTest);
		persist(usuario);

		usuario = new User();
		usuario.setId(null);
		usuario.setName(nomeUniqueTest);
		usuario.setEmail(emailUniqueTest);
		persist(usuario);

	}

}
