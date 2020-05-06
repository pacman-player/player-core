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
public class RoleServiceImpl extends AbstractServiceImpl<Long, Role, RoleDao> implements RoleService {

	private final RoleDtoDao roleDtoDao;

	@Autowired
	public RoleServiceImpl(RoleDao dao, RoleDtoDao roleDtoDao) {
		super(dao);
		this.roleDtoDao = roleDtoDao;
	}

	@Override
	public Role getByName(String roleName) {
		return dao.getRoleByName(roleName);
	}

	@Override
	public List<RoleDto> getAllRolesDto() {
		return roleDtoDao.getAllRoles();
	}

}
