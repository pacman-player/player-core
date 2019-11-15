package spring.app.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.UserDao;
import spring.app.model.User;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;


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
	public List<User> getAllUsers() {

		return (List<User>) entityManager.createNativeQuery("SELECT * FROM users", User.class).getResultList();
	}
}
