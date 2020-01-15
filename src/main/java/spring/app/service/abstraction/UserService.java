package spring.app.service.abstraction;


import spring.app.dto.UserRegistrationDto;
import spring.app.model.User;

import java.util.List;

public interface UserService {
	User getUserByLogin(String login);

	User getUserByGoogleId(String googleId);

    User getUserByVkId(int vkId);

	User getUserById(Long id);

	void addUser(User user);

    User getUserByEmail(String email);

    void save(UserRegistrationDto registration);

	List<User> getAllUsers();

	void deleteUserById(Long id);

	void updateUser(User user);

	Long getIdAuthUser();
}
