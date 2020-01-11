package spring.app.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.SongCompilationDao;
import spring.app.model.SongCompilation;

import javax.persistence.TypedQuery;
import java.util.List;


@Repository
@Transactional
@SuppressWarnings("unchecked")
public class SongCompilationDaoImpl extends AbstractDao<Long, SongCompilation> implements SongCompilationDao {

    SongCompilationDaoImpl() {
        super(SongCompilation.class);
    }

    @Override
    public List<SongCompilation> getListSongCompilationsByGenreId(Long id) {
        TypedQuery<SongCompilation> query = entityManager.createQuery("FROM SongCompilation WHERE genre_id = :id", SongCompilation.class);
        query.setParameter("id", id);
        List<SongCompilation> list = query.getResultList();
        return list;
    }

    @Override
    public void deleteValByGenreId(Long id) {
        TypedQuery<SongCompilation> query = (TypedQuery<SongCompilation>) entityManager.createQuery("UPDATE SongCompilation SET genre_id = null WHERE genre_id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public SongCompilation getSongCompilationByCompilationName(String compilationName) {
        List<SongCompilation> songCompilationList = entityManager.createQuery("FROM SongCompilation WHERE name = :compilationName", SongCompilation.class)
                .setParameter("compilationName", compilationName)
                .getResultList();
        return songCompilationList.isEmpty() ? null : songCompilationList.get(0);
    }
}
