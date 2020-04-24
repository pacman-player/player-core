package spring.app.dao.abstraction.dto;

import spring.app.dto.UserDto;

import java.util.List;

public interface UserDtoDao {

    List<UserDto> getAllUsers();

    UserDto getById(Long id);

}
