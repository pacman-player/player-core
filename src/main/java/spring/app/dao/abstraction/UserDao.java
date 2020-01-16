package spring.app.dao.abstraction;

import spring.app.model.User;

public interface UserDao extends GenericDao<Long, User> {
	User getUserByLogin(String login);
	User getUserByGoogleId(String googleId);
	User getByEmail(String email);
    User getUserByVkId(int vkId);
	boolean isExistUserByEmail(String email);
	boolean isExistUserByLogin(String login);
}
