package spring.app.dto.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dto.UserDto;
import spring.app.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class UserDtoDao {
    @PersistenceContext
    private EntityManager entityManager;

    public boolean isExistUserByEmail(String email) {
        Query query = entityManager.createQuery(
                "SELECT new spring.app.dto.UserDto(" +
                        "a.email" +
                        ") FROM " + User.class.getName() + " a WHERE a.email = :email",
                UserDto.class
        );
        query.setParameter("email", email);
        if ((query).getResultList().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isExistUserBylogin(String login) {
        Query query = entityManager.createQuery(
                "SELECT new spring.app.dto.UserDto(" +
                        "a.login" +
                        "a.email" +
                        ") FROM " + User.class.getName() + " a WHERE a.login = :login",
                UserDto.class
        );
        query.setParameter("login", login);
        if ((query).getResultList().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
}
