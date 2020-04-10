package core.app.dao.abstraction;

import core.app.model.Role;

public interface RoleDao extends GenericDao<Long, Role> {
	Role getRoleByName(String roleName);
}
