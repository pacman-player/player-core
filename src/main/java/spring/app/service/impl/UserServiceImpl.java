package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.RoleDao;
import spring.app.dao.abstraction.UserDao;
import spring.app.dto.UserRegistrationDto;
import spring.app.model.Role;
import spring.app.model.User;
import spring.app.service.abstraction.UserService;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private PasswordEncoder passwordEncoder;

    private UserDao userDao;
    private RoleDao roleDao;
    private Role userRole;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User getUserByLogin(String login) {
        return userDao.getUserByLogin(login);
    }

    @Override
    public User getUserByGoogleId(String googleId) {
        return userDao.getUserByGoogleId(googleId);
    }

    public User getUserByEmail(String email) {
        return userDao.getByEmail(email);
    }

    public void save(UserRegistrationDto registration) {
        User user = new User(registration.getEmail(), registration.getLogin(), passwordEncoder.encode(registration.getPassword()), true);
        if (userRole == null) {
            userRole = roleDao.getRoleByName("A");
        }
        user.setRoles(Collections.singleton(userRole));
        userDao.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userDao.getById(id);
    }

    @Override
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.update(user);
    }

	@Override
	public User getUserByVkId(int vkId) {
		return userDao.getUserByVkId(vkId);
	}

    @Override
    public Long getIdAuthUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User authUser = (User) principal;
        return authUser.getId();
    }
    @Override
    public boolean isExistUserByEmail(String email){
        return userDao.isExistUserByEmail(email);
    }

    @Override
    public boolean isExistUserByEmail(String email, long userId) {
        return userDao.isExistUserByEmail(email, userId);
    }

    @Override
    public boolean isExistUserByLogin(String login){
        return userDao.isExistUserByLogin(login);
    }

    @Override
    public boolean isExistUserByLogin(String login, long userId) {
        return userDao.isExistUserByLogin(login, userId);
    }
}
