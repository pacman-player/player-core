package spring.app.dao.abstraction.dto;

import spring.app.dto.UserDto;

import java.util.List;

public interface UserDtoDao {

    boolean isExistUserByEmail(String email);

    boolean isExistUserByLogin(String login);

    List<UserDto> getAllUsers();

    UserDto getById(Long id);

}
