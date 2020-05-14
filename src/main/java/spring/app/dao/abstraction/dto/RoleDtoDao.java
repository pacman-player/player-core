package spring.app.dao.abstraction.dto;

import spring.app.dto.RoleDto;
import spring.app.model.Role;

import java.util.List;

public interface RoleDtoDao {

    List<RoleDto> getAllRoles();

    Role getRoleByName(String roleName);
}
