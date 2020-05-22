package spring.app.service.abstraction;

import spring.app.dto.RoleDto;
import spring.app.model.Role;

import java.util.List;

public interface RoleService extends GenericService<Long, Role> {

    Role getRoleByName(String roleName);

    List<RoleDto> getAllRolesDto();

    RoleDto getRoleDtoByName(String roleName);

    RoleDto getRoleDtoById(Long id);
}
