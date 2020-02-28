package spring.app.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.UserDao;
import spring.app.model.User;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class UserDaoImpl extends AbstractDao<Long, User> implements UserDao {
    private final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);

    public UserDaoImpl() {
        super(User.class);
    }

    @Override
    public User getUserByLoginWithRegStepsCompany(String login) {
        List<User> userList = entityManager.createQuery(/*"FROM User WHERE login = :login"*/
                "SELECT usr FROM User usr " +
                        "LEFT JOIN FETCH usr.registrationSteps " +
                        "LEFT JOIN FETCH usr.company " +
                        "WHERE usr.login = :login", User.class)
                .setParameter("login", login)
                .getResultList();
        if (userList.isEmpty()) {
            return null;
        }
        return userList.get(0);
    }

    @Override
    public User getUserByLogin(String login) {
        List<User> userList = entityManager.createQuery("FROM User WHERE login = :login", User.class)
                .setParameter("login", login)
                .getResultList();
        if (userList.isEmpty()) {
            return null;
        }
        return userList.get(0);
    }

    @Override
    public User getUserByGoogleId(String googleId) {
        User user;
        try {
            TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.googleId = :googleId", User.class);
            query.setParameter("googleId", googleId);
            user = query.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.warn(e.getMessage(), e);
            return null;
        }
        return user;
    }

    @Override
    public User getByEmail(String email) {
        List<User> userList = entityManager.createQuery("FROM User WHERE email = :email", User.class)
                .setParameter("email", email)
                .getResultList();
        if (userList.isEmpty()) {
            return null;
        }
        return userList.get(0);
    }

    @Override
    public User getUserByVkId(int vkId) {
        List<User> userList = entityManager.createQuery("FROM User WHERE vkId = :vkId", User.class)
                .setParameter("vkId", vkId)
                .getResultList();
        if (userList.isEmpty()) {
            return null;
        }
        return userList.get(0);
    }

    @Override
    public boolean isExistUserByEmail(String email) {
        long count = (long) entityManager.createQuery(
                "select count(u) from User u WHERE u.email=:email")
                .setParameter("email", email)
                .getSingleResult();
        return count > 0;
    }

    @Override
    public boolean isExistUserByEmail(String email, long userId) {
        long count = (long) entityManager.createQuery(
                "select count(u) from User u WHERE u.email=:email AND u.id<>:id")
                .setParameter("email", email)
                .setParameter("id", userId)
                .getSingleResult();
        System.out.println(count);
        return count > 0;
    }

    @Override
    public boolean isExistUserByLogin(String login) {
        long count = (long) entityManager.createQuery(
                "select count(u) from User u WHERE u.login=:login")
                .setParameter("login", login)
                .getSingleResult();
        return count > 0;
    }

    @Override
    public boolean isExistUserByLogin(String login, long userId) {
        long count = (long) entityManager.createQuery(
                "select count(u) from User u WHERE u.login=:login AND u.id<>:id")
                .setParameter("login", login)
                .setParameter("id", userId)
                .getSingleResult();
        return count > 0;
    }

    @Override
    public List<User> getUserByRole(String role) {
        List<User> userList = entityManager.createQuery("SELECT usr FROM User usr LEFT JOIN fetch usr.roles rl WHERE rl.name = :name", User.class)
                .setParameter("name", role)
                .getResultList();
        if (userList.isEmpty()) {
            return null;
        }
        return userList;
    }


}
