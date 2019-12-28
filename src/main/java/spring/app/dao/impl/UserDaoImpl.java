package spring.app.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.UserDao;
import spring.app.model.User;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

@Repository
@Transactional
public class UserDaoImpl extends AbstractDao<Long, User> implements UserDao {
	private final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);

	public UserDaoImpl() {
		super(User.class);
	}

	@Override
	public User getUserByLogin(String login) {
		User user = null;
		try {
			TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.login = :login OR u.email = :login", User.class);
			query.setParameter("login", login);
			user = query.getSingleResult();
		} catch (NoResultException e) {
			LOGGER.error(e.getMessage());
		}
		return user;
	}

	@Override
	public User getUserByGoogleId(String googleId) {
		User user = null;
		try {
			TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.googleId = :googleId", User.class);
			query.setParameter("googleId", googleId);
			user = query.getSingleResult();
		} catch (NoResultException e) {
			LOGGER.error(e.getMessage());
		}
		return user;
	}

	@Override
	public User getUserByVkId(int vkId) {
		User user = null;
		try {
			TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.vkId = :vkId", User.class);
			query.setParameter("vkId", vkId);
			user = query.getSingleResult();
		} catch (NoResultException e) {
			LOGGER.error(e.getMessage());
		}
		return user;
	}
}
