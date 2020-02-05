package spring.app.actuator.endpoints.songDaoEndpoint;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import spring.app.actuator.endpoints.util.EndPointTestFiller;
import spring.app.actuator.endpoints.util.PreparedTestHeader;
import spring.app.dao.abstraction.SongDao;
import spring.app.model.Song;
import spring.app.model.User;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
@Component
@Transactional
public class HibHealthEndpointSongDao extends AbstractEndpoint<List<String>> {

    private SongDao songDao;

    @Autowired
    private PreparedTestHeader preparedTestHeader;
    @Autowired
    public void setSongDao(SongDao songDao) {
        this.songDao = songDao;
    }

    public HibHealthEndpointSongDao() {
        super("songDaoHibHealth", false, true);
    }

    @Override
    public List<String> invoke() {
        List<String> result = new ArrayList<>();
        User user = new User();
        Session session = preparedTestHeader.getHeader("songDao", result, user);

        //check 1 (N+1)
        session.getSessionFactory().getStatistics().clear();
        songDao.getByName("songName");
        EndPointTestFiller.fillStats(result, session, "getByName", "N+1");

        //check 2 (N+1 & Batching)
        session.getSessionFactory().getStatistics().clear();
        List<Song> findedList = songDao.findByNameContaining("er");
        EndPointTestFiller.fillStats(result, session, "findByNameContaining", "N+1", "Batching:" + findedList.size());

        //check 3 (N+1 & Batching)
        session.getSessionFactory().getStatistics().clear();
        List<Song> songList = songDao.getAll();
        EndPointTestFiller.fillStats(result, session, "getAll", "N+1", "Batching:" + songList.size());

        return result;
    }
}
