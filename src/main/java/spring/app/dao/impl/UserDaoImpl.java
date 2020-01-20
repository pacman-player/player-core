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
}
