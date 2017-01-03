/*
 * Demoiselle Framework
 *
 * License: GNU Lesser General Public License (LGPL), version 3 or later.
 * See the lgpl.txt file in the root directory or <https://www.gnu.org/licenses/lgpl.html>.
 */
package org.demoiselle.jee7.example.store.user.dao.entityManager;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.demoiselle.component.tenant.dao.context.EntityManagerMaster;

public class EntityManagerMasterDAO implements EntityManagerMaster {

	@PersistenceContext(unitName = "UserMasterPU")
	protected EntityManager emEntity;

	public EntityManager getEntityManager() {
		return emEntity;
	}

}
