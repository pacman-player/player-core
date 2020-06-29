package spring.app.dao.abstraction;

import spring.app.model.Role;

public interface RoleDao extends GenericDao<Long, Role> {
    Role getRoleByName(String roleName);
}
