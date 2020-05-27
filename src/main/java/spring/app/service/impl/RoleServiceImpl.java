package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.app.dao.abstraction.RoleDao;
import spring.app.dao.abstraction.dto.RoleDtoDao;
import spring.app.dto.RoleDto;
import spring.app.model.Role;
import spring.app.service.abstraction.RoleService;

import java.util.List;

@Service
public class RoleServiceImpl extends AbstractServiceImpl<Long, Role, RoleDao> implements RoleService {

	private final RoleDtoDao roleDtoDao;
	private final RoleDao roleDao;

	@Autowired
	public RoleServiceImpl(RoleDao dao, RoleDtoDao roleDtoDao) {
		super(dao);
		this.roleDtoDao = roleDtoDao;
		this.roleDao = dao;
	}



	// для проверки кастомного ответа на выбрасывание ошибки приложением.
	@Override
	public Role getRoleByName(String roleName) {
		return roleDao.getRoleByName(roleName);

	}

	@Override
	public List<RoleDto> getAllRolesDto() {
		return roleDtoDao.getAllRolesDto();
	}

	@Override
	public RoleDto getRoleDtoByName(String roleName) {
		return roleDtoDao.getRoleDtoByName(roleName);
	}

	@Override
	public RoleDto getRoleDtoById(Long id) {
		return roleDtoDao.getRoleDtoById(id);
	}

}
