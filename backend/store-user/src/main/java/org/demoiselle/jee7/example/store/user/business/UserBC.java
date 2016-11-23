/*
 * Demoiselle Framework
 *
 * License: GNU Lesser General Public License (LGPL), version 3 or later.
 * See the lgpl.txt file in the root directory or <https://www.gnu.org/licenses/lgpl.html>.
 */
package org.demoiselle.jee7.example.store.user.business;

import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import javax.ws.rs.core.Response;

import org.demoiselle.jee.multitenancy.hibernate.context.MultiTenantContext;
import org.demoiselle.jee.multitenancy.hibernate.entity.Tenant;
import org.demoiselle.jee.persistence.crud.AbstractBusiness;
import org.demoiselle.jee.script.DynamicManager;
import org.demoiselle.jee.security.exception.DemoiselleSecurityException;
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
public class UserBC extends AbstractBusiness<User, Long> {

	@Inject
	private UserDAO dao;

	@Resource
	private UserTransaction userTransaction;

	@Inject
	private MultiTenantContext multiTenantContext;

	@Inject
	private DynamicManager scriptManager;

	@Transactional
	@Override
	public User persist(User entity) {

		// Aplica o script no usuário do contexto de multitenancy
		// multiTenantContext
		try {
			
			Map<String, Object> confTenant = multiTenantContext.getTenant().getMappedConfiguration();
			
			if (confTenant.get("createUserScript") != null
					&& !confTenant.get("createUserScript").toString().isEmpty()) {

				String script = confTenant.get("createUserScript").toString();
				Tenant tenant = multiTenantContext.getTenant();

				SimpleBindings vars = new SimpleBindings();
				vars.put("usuario", entity);
				vars.put("tenant", tenant);

				String scriptId = "createUser-" + tenant.getName();

				// Verifica se existe o script no cache
				if (scriptManager.getScript(scriptId) == null) {
					System.out.println("Criado o script [" + scriptId + "].");

					scriptManager.loadEngine("groovy");
					scriptManager.loadScript(scriptId, script);
				}

				scriptManager.eval(scriptId, vars);
			}
		} catch (ScriptException e) {
			e.printStackTrace();
		}

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

	public User loadByEmailAndSenha(String email, String senha) {
		User u = dao.loadByEmailAndSenha(email, senha);

		if (u == null) {
			throw new DemoiselleSecurityException("Usuário não existe", Response.Status.UNAUTHORIZED.getStatusCode());
		}

		return u;
	}

}
