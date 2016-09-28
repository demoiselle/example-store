/*
 * Demoiselle Framework
 *
 * License: GNU Lesser General Public License (LGPL), version 3 or later.
 * See the lgpl.txt file in the root directory or <https://www.gnu.org/licenses/lgpl.html>.
 */
package org.demoiselle.store.usuario.business;

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

import org.demoiselle.store.usuario.crud.GenericCrudBusiness;
import org.demoiselle.store.usuario.dao.UsuarioDAO;
import org.demoiselle.store.usuario.dao.context.PersistenceContextDAO;
import org.demoiselle.store.usuario.entity.Usuario;

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
public class UsuarioBC extends GenericCrudBusiness<Usuario> {

	@Inject
	private UsuarioDAO dao;

	@Resource
	private UserTransaction userTransaction;

	@Override
	protected PersistenceContextDAO<Usuario> getPersistenceDAO() {
		return dao;
	}

	public void createTesteTransacional1(Usuario usuario)
			throws SecurityException, IllegalStateException, RollbackException, HeuristicMixedException,
			HeuristicRollbackException, SystemException, NotSupportedException {

		userTransaction.begin();
		getPersistenceDAO().create(usuario);
		userTransaction.commit();

		userTransaction.begin();
		
		UUID uuid = UUID.randomUUID();
		String emailUniqueTest = uuid.toString() + "@teste.com.br";
		String nomeUniqueTest = "Nome " + uuid.toString();

		usuario = new Usuario();
		usuario.setId(null);
		usuario.setNome(nomeUniqueTest);
		usuario.setEmail(emailUniqueTest);
		getPersistenceDAO().create(usuario);

		usuario = new Usuario();
		usuario.setId(null);
		usuario.setNome(nomeUniqueTest);
		usuario.setEmail(emailUniqueTest);
		getPersistenceDAO().create(usuario);

		userTransaction.commit();
	}

	public void createTesteTransacional2(Usuario usuario) {
		getPersistenceDAO().create(usuario);
	}

	@Transactional
	public void createTesteTransacional3(Usuario usuario) {

		getPersistenceDAO().create(usuario);

		UUID uuid = UUID.randomUUID();
		String emailUniqueTest = uuid.toString() + "@teste.com.br";
		String nomeUniqueTest = "Nome " + uuid.toString();

		usuario = new Usuario();
		usuario.setId(null);
		usuario.setNome(nomeUniqueTest);
		usuario.setEmail(emailUniqueTest);
		getPersistenceDAO().create(usuario);

		usuario = new Usuario();
		usuario.setId(null);
		usuario.setNome(nomeUniqueTest);
		usuario.setEmail(emailUniqueTest);
		getPersistenceDAO().create(usuario);

	}

}
