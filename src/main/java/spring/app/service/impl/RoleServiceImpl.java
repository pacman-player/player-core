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
public class RoleServiceImpl extends AbstractServiceImpl<Role, RoleDao, Long> implements RoleService {

    @Autowired
    public RoleServiceImpl(RoleDao dao) {
        super(dao);
    }

    @Override
    public Role geByName(String roleName) {
        return dao.getRoleByName(roleName);
    }

}
