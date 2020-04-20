package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.NotificationDao;
import spring.app.dao.abstraction.RoleDao;
import spring.app.dao.abstraction.UserDao;
import spring.app.dto.UserRegistrationDto;
import spring.app.model.Role;
import spring.app.model.User;
import spring.app.service.abstraction.CompanyService;
import spring.app.service.abstraction.UserService;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl  extends AbstractService<User, UserDao> implements UserService{

    private PasswordEncoder passwordEncoder;
    private RoleDao roleDao;
    private NotificationDao notificationDao;
    private Role userRole;
    private CompanyService companyService;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao, NotificationDao notificationDao, CompanyService companyService) {
        super(userDao);
        this.roleDao = roleDao;
        this.notificationDao = notificationDao;
        this.companyService = companyService;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User getUserByLoginWithRegStepsCompany(String login) {
        return dao.getUserByLoginWithRegStepsCompany(login);
    }

    @Override
    public User getUserByLogin(String login) {
        return dao.getUserByLogin(login);
    }

    @Override
    public User getUserByGoogleId(String googleId) {
        return dao.getUserByGoogleId(googleId);
    }

    @Override
    public User getUserByEmail(String email) {
        return dao.getByEmail(email);
    }

    @Override
    public void save(UserRegistrationDto userRegistrationDto) {
        User user = new User(userRegistrationDto.getEmail(), userRegistrationDto.getLogin(), passwordEncoder.encode(userRegistrationDto.getPassword()), true);

        if (userRole == null) {
            userRole = roleDao.getRoleByName("USER");
        }
        user.setRoles(Collections.singleton(userRole));
        dao.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return dao.getById(id);
    }

    @Override
    public void addUser(User user) {

        if (user.getPassword() != null && !user.getPassword().startsWith("$2a$")) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        dao.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return dao.getAll();
    }

    @Override
    public void deleteUserById(Long id) {
        notificationDao.bulkRemoveNotificationsByUserId(id);
        if (dao.getById(id).getCompany() != null) {
            companyService.removeById(dao.getById(id).getCompany().getId());
        }
        dao.deleteById(id);
    }

    @Override
    public void updateUser(User user) {

        if (user.getPassword() != null && !user.getPassword().startsWith("$2a$")) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        dao.update(user);
    }

    @Override
    public void updateUserWithEncodePassword(User user) {
        dao.update(user);
    }

    //метод для обновления недорегенного юзера с зашифрованным паролем
    @Override
    public void addUserWithEncodePassword(User user) {
        dao.save(user);
    }

    @Override
	public User getUserByVkId(int vkId) {
		return dao.getUserByVkId(vkId);
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
        return dao.isExistUserByEmail(email);
    }

    @Override
    public boolean isExistUserByEmail(String email, long userId) {
        return dao.isExistUserByEmail(email, userId);
    }

    @Override
    public boolean isExistUserByLogin(String login){
        return dao.isExistUserByLogin(login);
    }

    @Override
    public boolean isExistUserByLogin(String login, long userId) {
        return dao.isExistUserByLogin(login, userId);
    }

    @Override
    public List<User> getUserByRole(String role) {
        return dao.getUserByRole(role);
    }


}
