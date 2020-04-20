package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.RegistrationStepDao;
import spring.app.dao.abstraction.RoleDao;
import spring.app.model.RegistrationStep;
import spring.app.model.Role;
import spring.app.service.abstraction.RoleService;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl extends AbstractServiceImpl<Role, RoleDao> implements RoleService {

    @Autowired
    public RoleServiceImpl(RoleDao dao) {
        super(dao);
    }

    @Override
    public void addRole(Role role) {
        dao.save(role);
    }

    @Override
    public Role getRoleByName(String roleName) {
        return dao.getRoleByName(roleName);
    }

    @Override
    public Role getRoleById(Long id) {
        return dao.getById(id);
    }

    @Override
    public List<Role> getAllRoles() {
        return dao.getAll();
    }

    @Override
    public void updateRole(Role role) {
        dao.update(role);
    }

    @Override
    public void deleteRoleById(Long id) {
        dao.deleteById(id);
    }
}
