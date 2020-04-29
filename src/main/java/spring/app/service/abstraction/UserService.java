package spring.app.service.abstraction;


import spring.app.dto.UserDto;
import spring.app.dto.UserRegistrationDto;
import spring.app.model.User;

import java.util.List;

public interface UserService extends GenericService<Long, User>{
    User getUserByLoginWithRegStepsCompany(String login);

    User getUserByLogin(String login);

    User getUserByGoogleId(String googleId);

    User getUserByVkId(int vkId);

    UserDto getUserDtoById(Long id);

    void addUserWithEncodePassword(User user);

    User getUserByEmail(String email);

    void save(UserRegistrationDto registration);

    List<UserDto> getAllUsers();

    void updateUserWithEncodePassword(User user);

    Long getIdAuthUser();

    boolean isExistUserByEmail(String email);

    boolean isExistUserByEmail(String email, long userId);

    boolean isExistUserByLogin(String login);

    boolean isExistUserByLogin(String login, long userId);

    List<User> getUserByRole(String role);
}
