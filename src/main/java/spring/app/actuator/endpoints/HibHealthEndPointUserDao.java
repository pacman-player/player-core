package spring.app.actuator.endpoints;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.UserDao;
import spring.app.model.User;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class HibHealthEndPointUserDao extends AbstractEndpoint<List<String>> {
    @Autowired
    private UserDao userDao;

    @Autowired
    EntityManager entityManager;

    public HibHealthEndPointUserDao() {
        super("userDaoHibHealth", false, true);
    }

    @Override
    public List<String> invoke() {
        List<String> result = new ArrayList<>();
        Session session = entityManager.unwrap(Session.class);
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        result.add("LOGIN: " + user.getUsername());

        //check 1
        session.getSessionFactory().getStatistics().clear();
        user = userDao.getUserByLogin(user.getUsername());
        fillInH1SessionStatistics(result, session, "getUserByLogin");

        //check 2
        session.getSessionFactory().getStatistics().clear();
        userDao.getByEmail(user.getEmail());
        fillInH1SessionStatistics(result, session, "getByEmail");

        //check 3
        session.getSessionFactory().getStatistics().clear();
        userDao.isExistUserByEmail(user.getEmail());
        fillInH1SessionStatistics(result, session, "isExistUserByEmail(1 arg)");

        //check 4
        session.getSessionFactory().getStatistics().clear();
        userDao.isExistUserByEmail(user.getEmail(), user.getId());
        fillInH1SessionStatistics(result, session, "isExistUserByEmail(2 args)");

        //check 5
        session.getSessionFactory().getStatistics().clear();
        if (user.getGoogleId() != null) {
            userDao.getUserByGoogleId(user.getGoogleId());
        }
        fillInH1SessionStatistics(result, session, "getUserByGoogleId");

        //check 6
        session.getSessionFactory().getStatistics().clear();
        if (user.getVkId() != null) {
            userDao.getUserByVkId(user.getVkId());
        }
        fillInH1SessionStatistics(result, session, "isExistUserByVKId");

        //check 7
        session.getSessionFactory().getStatistics().clear();
            userDao.isExistUserByLogin(user.getLogin());
        fillInH1SessionStatistics(result, session, "isExistUserByLogin(1 arg)");

        //check 8
        session.getSessionFactory().getStatistics().clear();
        userDao.isExistUserByLogin(user.getLogin(), user.getId());
        fillInH1SessionStatistics(result, session, "isExistUserByLogin(2 args");

        return result;
    }

    private void fillInH1SessionStatistics(List<String> resultLIst, Session currentSession, String methodName) {
        if (currentSession.getSessionFactory().getStatistics().getQueries().length != 0) {
            long ratio = currentSession.getSessionFactory().getStatistics().getPrepareStatementCount() / currentSession.getSessionFactory().getStatistics().getQueries().length;
            resultLIst.add(String.format("Method name: %s, query/statement ratio = %s/%s, N+1: %s",
                    methodName,
                    currentSession.getSessionFactory().getStatistics().getQueries().length,
                    currentSession.getSessionFactory().getStatistics().getPrepareStatementCount(),
                    ratio > 1 ? "FAILED" : "PASSED"));
        }
    }
}
