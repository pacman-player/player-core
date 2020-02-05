package spring.app.actuator.endpoints.util;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import spring.app.model.User;

import javax.persistence.EntityManager;
import java.util.List;

@Component
public class PreparedTestHeader {

    private EntityManager entityManager;

    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Session getHeader (String daoName, List<String> result, User user) {
        Session session = entityManager.unwrap(Session.class);
        user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        result.add("LOGIN: " + user.getUsername() + ", " + "DAO: " + daoName);
        return session;
    }
}
