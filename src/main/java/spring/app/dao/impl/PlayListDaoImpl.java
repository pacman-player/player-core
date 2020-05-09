package spring.app.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.PlayListDao;
import spring.app.model.PlayList;
import spring.app.model.Song;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class PlayListDaoImpl extends AbstractDao<Long, PlayList> implements PlayListDao {

    PlayListDaoImpl() {
        super(PlayList.class);
    }

    @Override
    public List<PlayList> getAll() {
        return super.getAll();
    }

    @Override
    public PlayList getByName(String name) {
        TypedQuery<PlayList> query = entityManager.createQuery("from PlayList where name = :name", PlayList.class);
        query.setParameter("name", name);
        PlayList playList;
        try {
            playList = query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return playList;
    }
}
