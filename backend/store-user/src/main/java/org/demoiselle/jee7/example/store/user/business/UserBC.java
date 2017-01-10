/*
 * Demoiselle Framework
 *
 * License: GNU Lesser General Public License (LGPL), version 3 or later.
 * See the lgpl.txt file in the root directory or <https://www.gnu.org/licenses/lgpl.html>.
 */
package org.demoiselle.jee7.example.store.user.business;

import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import org.demoiselle.jee.core.exception.DemoiselleException;
import org.demoiselle.jee.crud.AbstractBusiness;
import org.demoiselle.jee.script.DynamicManager;
import org.demoiselle.jee.security.exception.DemoiselleSecurityException;
import org.demoiselle.jee7.example.store.user.dao.UserDAO;
import org.demoiselle.jee7.example.store.user.entity.User;
import org.demoiselle.tenant.hibernate.context.MultiTenantContext;
import org.demoiselle.tenant.hibernate.entity.Tenant;

@Stateless
@TransactionManagement(value = TransactionManagementType.CONTAINER)
public class UserBC extends AbstractBusiness<User, Long> {

	@Inject
	private UserDAO dao;

	@Inject
	private MultiTenantContext multiTenantContext;

	@Inject
	private DynamicManager scriptManager;

	@Transactional
	public User persist(User entity) {

		if (multiTenantContext == null || multiTenantContext.getTenant() == null) {
			throw new DemoiselleException("É necessário selecionar um Tenant antes de executar a operação.");
		}

		// Aplica o script no usuário do contexto de multitenancy
		// multiTenantContext
		try {

			Map<String, Object> confTenant = multiTenantContext.getTenant().getMappedConfiguration();

			if (confTenant != null && confTenant.get("createUserScript") != null
					&& !confTenant.get("createUserScript").toString().isEmpty()) {

				String script = confTenant.get("createUserScript").toString();
				Tenant tenant = multiTenantContext.getTenant();

				SimpleBindings vars = new SimpleBindings();
				vars.put("usuario", entity);
				vars.put("tenant", tenant);

				String scriptId = "createUser-" + tenant.getName();

				scriptManager.loadScript("groovy", scriptId, script);
				scriptManager.eval("groovy", scriptId, vars);
			}
		} catch (ScriptException e) {
			e.printStackTrace();
		}

		return dao.persist(entity);
	}

	public User loadByEmailAndSenha(String email, String senha) {
		User u = dao.loadByEmailAndSenha(email, senha);
		if (u == null) {
			throw new DemoiselleSecurityException("Usuário não existe", Response.Status.UNAUTHORIZED.getStatusCode());
		}
		return u;
	}

	public User loadByEmail(String email) {
		return dao.loadByEmail(email);
	}

}
