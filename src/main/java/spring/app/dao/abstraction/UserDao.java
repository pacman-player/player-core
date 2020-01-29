package spring.app.dao.abstraction;

import spring.app.model.User;

import java.util.List;

public interface UserDao extends GenericDao<Long, User> {
	User getUserByLogin(String login);
	User getUserByGoogleId(String googleId);
	User getByEmail(String email);
    User getUserByVkId(int vkId);

    List<User> getAllDaoJoin();

	boolean isExistUserByEmail(String email);
	boolean isExistUserByEmail(String email, long userId);
	boolean isExistUserByLogin(String login);
	boolean isExistUserByLogin(String login, long userId);
}
