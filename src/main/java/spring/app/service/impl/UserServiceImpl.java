package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import spring.app.dao.abstraction.UserDao;
import spring.app.dto.UserRegistrationDto;
import spring.app.model.Role;
import spring.app.model.User;
import spring.app.repository.UserRepository;
import spring.app.service.abstraction.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

	private UserDao userDao;

	@Autowired
	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}
	private UserRepository userRepository;



	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public User getUserByLogin(String login) {
		return userDao.getUserByLogin(login);
	}

    @Override
    public User getUserByGoogleId(String googleId) {
        return userDao.getUserByGoogleId(googleId);
    }

	public String findByEmail(String email){
		return userDao.findByEmail(email);
	}

	public User save(UserRegistrationDto registration){
		User user = new User("rint", "sd", "sd", true);
//		user.setLogin(registration.getFirstName());
//		user.setEmail(registration.getEmail());
//		user.setPassword(registration.getPassword());
//		user.setGoogleId("goog");

		return userRepository.save(user);
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
		return userDao.getAll();
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
