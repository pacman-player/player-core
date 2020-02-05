package spring.app.actuator.endpoints.authoeDaoEndPoint;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import spring.app.actuator.endpoints.util.EndPointTestFiller;
import spring.app.actuator.endpoints.util.PreparedTestHeader;
import spring.app.dao.abstraction.AuthorDao;
import spring.app.model.User;

import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class HibHealthEndPointAuthorDao extends AbstractEndpoint<List<String>> {

    @Autowired
    private AuthorDao authorDao;
    @Autowired
    private PreparedTestHeader preparedTestHeader;

    public HibHealthEndPointAuthorDao() {
        super("authorDaoHibHealth", false, true);
    }

    @Override
    public List<String> invoke() {

        List<String> result = new ArrayList<>();
        User user = new User();
        Session session = preparedTestHeader.getHeader("authorDao", result, user);

        //check 1 (N+1)
        session.getSessionFactory().getStatistics().clear();
        authorDao.getByName("author-1");
        EndPointTestFiller.fillStats(result, session, "getByName", "N+1");

        //check 2 (N+1 & Batching)
        session.getSessionFactory().getStatistics().clear();
        long authorsList = authorDao.getAll().size();
        EndPointTestFiller.fillStats(result, session, "getAll", "N+1", "Batching:" + authorsList);

        return result;
    }
}
