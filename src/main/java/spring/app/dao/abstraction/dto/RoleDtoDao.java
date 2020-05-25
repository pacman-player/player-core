package spring.app.dao.abstraction.dto;

import spring.app.dto.RoleDto;

import java.util.List;

public interface RoleDtoDao {

    List<RoleDto> getAllRolesDto();

    RoleDto getRoleDtoByName(String roleName);

    RoleDto getRoleDtoById(Long id);
}
