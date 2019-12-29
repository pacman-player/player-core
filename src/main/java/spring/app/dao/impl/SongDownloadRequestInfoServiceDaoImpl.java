package spring.app.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.SongDownloadRequestInfoServiceDao;
import spring.app.model.SongDownloadRequestInfo;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
public class SongDownloadRequestInfoServiceDaoImpl extends AbstractDao<Long, SongDownloadRequestInfo> implements SongDownloadRequestInfoServiceDao {


    SongDownloadRequestInfoServiceDaoImpl() {
        super(SongDownloadRequestInfo.class);
    }

    @Override
    public List<SongDownloadRequestInfo> getBySongName(String songName) {

        List<SongDownloadRequestInfo> resultList = null;
            try {
                TypedQuery<SongDownloadRequestInfo> query = entityManager.createQuery("SELECT s FROM SongDownloadRequestInfo s WHERE s.song_name = :song_name", SongDownloadRequestInfo.class);
                query.setParameter("song_name", songName);
             resultList= query.getResultList();
            } catch (NoResultException e) {
                //logger
            }

        return resultList;

    }

    @Override
    public List<SongDownloadRequestInfo> getByAuthorName(String authorName) {
        List<SongDownloadRequestInfo> resultList = null;
        try {
            TypedQuery<SongDownloadRequestInfo> query = entityManager.createQuery("SELECT s FROM SongDownloadRequestInfo s WHERE s.author_name = :author_name", SongDownloadRequestInfo.class);
            query.setParameter("author_name", authorName);
            resultList= query.getResultList();
        } catch (NoResultException e) {
            //logger
        }

        return resultList;
    }

    @Override
    public SongDownloadRequestInfo getBySongNameAndAuthorName(String songName, String authorName) {
        SongDownloadRequestInfo songDownloadRequestInfo = null;
        try {
            Query query = entityManager.createQuery("SELECT s FROM SongDownloadRequestInfo s WHERE s.author_name = :author_name and s.song_name = :song_name", SongDownloadRequestInfo.class);
            query.setParameter("author_name", authorName);
            query.setParameter("song_name", songName);
            songDownloadRequestInfo= (SongDownloadRequestInfo) query.getResultList().get(0);
        } catch (NoResultException e) {
            //logger
        }
        return songDownloadRequestInfo;
    }

    @Override
    public SongDownloadRequestInfo getById(String id) {
        SongDownloadRequestInfo result = null;
        try {
            TypedQuery<SongDownloadRequestInfo> query = entityManager.createQuery("SELECT s FROM SongDownloadRequestInfo s WHERE s.id = :id", SongDownloadRequestInfo.class);
            query.setParameter("id", id);
            result= query.getSingleResult();
        } catch (NoResultException e) {
            //logger
        }
        return result;
    }

    @Override
    public void deleteById(String id) {
        SongDownloadRequestInfo result = null;
        try {
            TypedQuery<SongDownloadRequestInfo> query = entityManager.createQuery("delete FROM SongDownloadRequestInfo WHERE id = :id", SongDownloadRequestInfo.class);
            query.setParameter("id", id);
            query.getSingleResult();
        } catch (NoResultException e) {
            //logger
        }
    }

    @Override
    public void deleteAll() {
        SongDownloadRequestInfo result = null;
        try {
            TypedQuery<SongDownloadRequestInfo> query = entityManager.createQuery("delete FROM SongDownloadRequestInfo", SongDownloadRequestInfo.class);
            query.getSingleResult();
        } catch (NoResultException e) {
            //logger
        }
    }
}
