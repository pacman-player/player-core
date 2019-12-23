package spring.app.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.UserDao;
import spring.app.model.User;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

@Repository
@Transactional
public class UserDaoImpl extends AbstractDao<Long, User> implements UserDao {

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
			//logger
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
			return null;
		}
		return user;
	}

	@Override
	public String findByEmail(String email) {

		User user = null;
		try {
			TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
			query.setParameter("email", email);
			user = query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		return email;


//		return userDao.findByEmail(email);

	}
}
