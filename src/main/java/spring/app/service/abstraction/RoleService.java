package spring.app.service.abstraction;

import spring.app.model.Role;

import java.util.List;

public interface RoleService extends GenericService<Long, Role> {

    Role geByName(String roleName);

}
