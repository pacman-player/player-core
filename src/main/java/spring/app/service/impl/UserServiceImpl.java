package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.app.dao.abstraction.RoleDao;
import spring.app.dao.abstraction.UserDao;
import spring.app.dto.UserRegistrationDto;
import spring.app.model.Role;
import spring.app.model.User;
import spring.app.service.abstraction.UserService;

import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private RoleDao roleDao;
    private Role userRole;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }


    @Override
    public User getUserByLogin(String login) {
        return userDao.getUserByLogin(login);
    }

    @Override
    public User getUserByGoogleId(String googleId) {
        return userDao.getUserByGoogleId(googleId);
    }

    public User getByEmail(String email) {
        return userDao.getByEmail(email);
    }

    public void save(UserRegistrationDto registration) {
        User user = new User(registration.getEmail(), registration.getLogin(), registration.getPassword(), true);
        if (userRole == null) {
            userRole = roleDao.getRoleByName("USER");
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
