package spring.app.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.RoleDao;
import spring.app.model.Role;

import javax.persistence.TypedQuery;


@Repository
@Transactional
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
