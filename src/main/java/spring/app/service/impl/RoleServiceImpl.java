package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.RoleDao;
import spring.app.dao.abstraction.dto.RoleDtoDao;
import spring.app.dto.RoleDto;
import spring.app.model.Role;
import spring.app.service.abstraction.RoleService;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

	private final RoleDao roleDao;
	private final RoleDtoDao roleDtoDao;

	@Autowired
	public RoleServiceImpl(RoleDao roleDao, RoleDtoDao roleDtoDao) {
		this.roleDao = roleDao;
		this.roleDtoDao = roleDtoDao;
	}

	@Override
	public void addRole(Role role) {
		roleDao.save(role);
	}

	@Override
	public Role getRoleByName(String roleName) {
		return roleDao.getRoleByName(roleName);
	}

	@Override
	public Role getRoleById(Long id) {
		return roleDao.getById(id);
	}

	@Override
	public List<Role> getAllRoles() {
		return roleDao.getAll();
	}

	@Override
	public List<RoleDto> getAllRolesDto() {
		return roleDtoDao.getAllRoles();
	}

	@Override
	public void updateRole(Role role) {
		roleDao.update(role);
	}

	@Override
	public void deleteRoleById(Long id) {
		roleDao.deleteById(id);
	}
}
