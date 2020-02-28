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

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements spring.app.service.abstraction.UserService {

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
    public User getUserByLoginWithRegStepsCompany(String login) {
        return userDao.getUserByLoginWithRegStepsCompany(login);
    }
    @Override
    public User getUserByLogin(String login) {
        return userDao.getUserByLogin(login);
    }

    @Override
    public User getUserByGoogleId(String googleId) {
        return userDao.getUserByGoogleId(googleId);
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.getByEmail(email);
    }

    @Override
    public void save(UserRegistrationDto userRegistrationDto) {
        User user = new User(userRegistrationDto.getEmail(), userRegistrationDto.getLogin(), passwordEncoder.encode(userRegistrationDto.getPassword()), true);

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

        if (user.getPassword() != null && !user.getPassword().startsWith("$2a$")) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
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

        if (user.getPassword() != null && !user.getPassword().startsWith("$2a$")) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userDao.update(user);
    }

    @Override
    public void updateUserWithEncodePassword(User user) {
        userDao.update(user);
    }

    //метод для обновления недорегенного юзера с зашифрованным паролем
    @Override
    public void addUserWithEncodePassword(User user) {
        userDao.save(user);
    }

    @Override
	public User getUserByVkId(int vkId) {
		return userDao.getUserByVkId(vkId);
	}

    @Override
    public Long getIdAuthUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(principal.toString());
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

    @Override
    public List<User> getUserByRole(String role) {
        return userDao.getUserByRole(role);
    }


}
