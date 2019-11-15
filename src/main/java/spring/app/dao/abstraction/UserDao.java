package spring.app.dao.abstraction;


import spring.app.model.User;

import java.util.List;

public interface UserDao extends GenericDao<Long, User> {
	User getUserByLogin(String login);

	List<User> getAllUsers();
}
