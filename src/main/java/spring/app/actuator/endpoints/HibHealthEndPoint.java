package spring.app.actuator.endpoints;

import org.hibernate.Session;
import org.hibernate.stat.Statistics;
import org.hibernate.stat.spi.StatisticsImplementor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.UserDao;
import spring.app.dao.impl.UserDaoImpl;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
@Component
@Transactional
public class HibHealthEndPoint extends AbstractEndpoint<List<String>> {
    @Autowired
    private UserDao userDao;

    private long count = 0;
    @Autowired
    EntityManager entityManager;

    public HibHealthEndPoint() {
        super("hibHealth", false, true);
    }

    @Override
    public List<String> invoke() {
        List<String> result = new ArrayList<>();
        Session session = entityManager.unwrap(Session.class);
        session.getSessionFactory().getStatistics().clear();
        userDao.getUserByLogin("admin");
        fillInH1SessionStatistics(result, session, "getUserByLogin");

        session.getSessionFactory().getStatistics().clear();
        userDao.getByEmail("admin@admin.com");
        fillInH1SessionStatistics(result, session, "getByEmail");


        return result;
    }

    private void fillInH1SessionStatistics(List<String> resultLIst, Session currentSession, String methodName) {
        long ratio = currentSession.getSessionFactory().getStatistics().getPrepareStatementCount()/currentSession.getSessionFactory().getStatistics().getQueries().length;
        resultLIst.add(String.format("%d. Method name: %s, query/statement ratio = %s/%s, N+1: %s",
                ++count,
                methodName,
                currentSession.getSessionFactory().getStatistics().getQueries().length,
                currentSession.getSessionFactory().getStatistics().getPrepareStatementCount(),
                ratio > 1 ? "FAILED" : "PASSED"));
    }
}
