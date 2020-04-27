package spring.app.service.abstraction;

import spring.app.dto.RoleDto;
import spring.app.model.Role;

import java.util.List;

public interface RoleService {
	void addRole(Role role);

	Role getRoleByName(String roleName);

	Role getRoleById(Long id);

	List<Role> getAllRoles();

	List<RoleDto> getAllRolesDto();

	void updateRole(Role role);

	void deleteRoleById(Long id);
}
