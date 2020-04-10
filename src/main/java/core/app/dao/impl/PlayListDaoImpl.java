package core.app.dao.impl;

import core.app.model.PlayList;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import core.app.dao.abstraction.PlayListDao;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional(readOnly = true)
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
