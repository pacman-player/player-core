package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.app.dao.abstraction.UserDao;
import spring.app.model.User;
import spring.app.service.abstraction.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

	private UserDao userDao;

	@Autowired
	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public User getUserByLogin(String login) {
		return userDao.getUserByLogin(login);
	}

	@Override
	public User getUserById(Long id) {
		return userDao.getById(id);
	}

	@Override
	public void addUser(User user) {
		userDao.save(user);
	}

	@Override
	public List<User> getAllUsers() {
		return userDao.getAllUsers();
	}

	@Override
	public void deleteUserById(Long id) {
		userDao.deleteById(id);
	}

	@Override
	public void updateUser(User user) {
		userDao.update(user);
	}

}
