package spring.app.service.abstraction;

import spring.app.dto.RoleDto;
import spring.app.model.Role;

import java.util.List;

public interface RoleService extends GenericService<Long, Role> {

    Role getByName(String roleName);

    List<RoleDto> getAllRolesDto();
}
