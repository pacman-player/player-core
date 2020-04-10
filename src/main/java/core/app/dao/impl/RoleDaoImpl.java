package core.app.dao.impl;

import core.app.model.Role;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import core.app.dao.abstraction.RoleDao;

import javax.persistence.TypedQuery;

@Repository
@Transactional(readOnly = true)
public class RoleDaoImpl extends AbstractDao<Long, Role> implements RoleDao {

	public RoleDaoImpl() {
		super(Role.class);
	}

	@Override
	public Role getRoleByName(String roleName) {
		TypedQuery<Role> query = entityManager.createQuery("SELECT r FROM Role r WHERE r.name = :name", Role.class);
		query.setParameter("name", roleName);
		return query.getSingleResult();
	}
}
