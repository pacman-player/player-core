package spring.app.actuator.endpoints.userDaoEndpoint;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import spring.app.actuator.endpoints.util.EndPointTestFiller;
import spring.app.dao.abstraction.UserDao;
import spring.app.model.User;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class HibHealthEndPointUserDao extends AbstractEndpoint<List<String>> {

    private UserDao userDao;

    private EntityManager entityManager;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public HibHealthEndPointUserDao() {
        super("userDaoHibHealth", false, true);
    }

    @Override
    public List<String> invoke() {
        List<String> result = new ArrayList<>();
        Session session = entityManager.unwrap(Session.class);
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        result.add("LOGIN: " + user.getUsername());

        //check 1 (N+1)
        session.getSessionFactory().getStatistics().clear();
        user = userDao.getUserByLogin(user.getUsername());
        EndPointTestFiller.fillStats(result, session, "getUserByLogin", "N+1");

        //check 2 (N+1)
        session.getSessionFactory().getStatistics().clear();
        userDao.getByEmail(user.getEmail());
        EndPointTestFiller.fillStats(result, session, "getByEmail", "N+1");

        //check 3 (N+1)
        session.getSessionFactory().getStatistics().clear();
        userDao.isExistUserByEmail(user.getEmail());
        EndPointTestFiller.fillStats(result, session, "isExistUserByEmail(1 arg)", "N+1");

        //check 4 (N+1)
        session.getSessionFactory().getStatistics().clear();
        userDao.isExistUserByEmail(user.getEmail(), user.getId());
        EndPointTestFiller.fillStats(result, session, "isExistUserByEmail(2 args)", "N+1");

        //check 5 (N+1)
        session.getSessionFactory().getStatistics().clear();
        if (user.getGoogleId() != null) {
            userDao.getUserByGoogleId(user.getGoogleId());
        }
        EndPointTestFiller.fillStats(result, session, "getUserByGoogleId", "N+1");

        //check 6 (N+1)
        session.getSessionFactory().getStatistics().clear();
        if (user.getVkId() != null) {
            userDao.getUserByVkId(user.getVkId());
        }
        EndPointTestFiller.fillStats(result, session, "isExistUserByVKId", "N+1");

        //check 7 (N+1)
        session.getSessionFactory().getStatistics().clear();
            userDao.isExistUserByLogin(user.getLogin());
        EndPointTestFiller.fillStats(result, session, "isExistUserByLogin(1 arg)", "N+1");

        //check 8 (N+1)
        session.getSessionFactory().getStatistics().clear();
        userDao.isExistUserByLogin(user.getLogin(), user.getId());
        EndPointTestFiller.fillStats(result, session, "isExistUserByLogin(2 args)", "N+1");

        //check 9 ((N+1 & Batching)
        session.getSessionFactory().getStatistics().clear();
        long entitiesInDb = userDao.getAll().size();
        EndPointTestFiller.fillStats(result, session, "getAll", "N+1", "Batching:" + entitiesInDb);

        return result;
    }
}
