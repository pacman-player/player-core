package spring.app.dao.impl;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;
import spring.app.dao.abstraction.PlayListDao;
import spring.app.model.PlayList;

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
        List<PlayList> list = super.getAll();
        for (PlayList p :
                list) {
            initLazyFields(p);
        }
        return list;
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
        initLazyFields(playList);
        return playList;
    }

    private void initLazyFields(PlayList p) {
        Hibernate.initialize(p.getSongCompilation());
    }
}
