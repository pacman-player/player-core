package core.app.service.abstraction;


import core.app.model.User;
import core.app.dto.UserRegistrationDto;

import java.util.List;

public interface UserService {
    User getUserByLoginWithRegStepsCompany(String login);

    User getUserByLogin(String login);

    User getUserByGoogleId(String googleId);

    User getUserByVkId(int vkId);

    User getUserById(Long id);

    void addUser(User user);

    void addUserWithEncodePassword(User user);

    User getUserByEmail(String email);

    void save(UserRegistrationDto registration);

    List<User> getAllUsers();

    void deleteUserById(Long id);

    void updateUser(User user);

    void updateUserWithEncodePassword(User user);

    Long getIdAuthUser();

    boolean isExistUserByEmail(String email);

    boolean isExistUserByEmail(String email, long userId);

    boolean isExistUserByLogin(String login);

    boolean isExistUserByLogin(String login, long userId);

	List<User> getUserByRole(String role);
}
