package spring.app.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.SongCompilationDao;
import spring.app.model.PlayList;
import spring.app.model.Song;
import spring.app.model.SongCompilation;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;


@Repository
@Transactional
public class SongCompilationDaoImpl extends AbstractDao<Long, SongCompilation> implements SongCompilationDao {

    SongCompilationDaoImpl() {
        super(SongCompilation.class);
    }

    @Override
    public List<SongCompilation> getListSongCompilationsByGenreId(Long id) {
        TypedQuery<SongCompilation> query = entityManager.createQuery("from SongCompilation where genre_id = :id", SongCompilation.class);
        query.setParameter("id", id);
        List<SongCompilation> list = query.getResultList();
        return list;
    }

//    @Override
//    public List<SongCompilation> getAllCompilationsInMorningPlaylist() {
//        TypedQuery<SongCompilation> query = entityManager.createQuery("from SongCompilation where genre_id = :id", SongCompilation.class);
//        query.setParameter("id", id);
//        List<SongCompilation> list = query.getResultList();
//        return allSongCompilations;
//    }

//    @Override
//    public void addSongCompilationToMorningPlaylist(Long id) {
//        String hql = "insert into song_compilation_on_play_list(play_list_id, song_compilation_id) select (select pl.id from play_list pl where id = 1), (select sc.id from song_compilation sc where id = 1)";
////        Query query = entityManager.createQuery("insert into song_compilation_on_play_list(play_list_id, song_compilation_id) select (select pl.id from play_list pl where id = 1), (select sc.id from song_compilation sc where id = ?)");
//        Query query = entityManager.createNativeQuery("insert into song_compilation_on_play_list(play_list_id, song_compilation_id) select (select pl.id from play_list pl where id = 1), (select sc.id from song_compilation sc where id = 1)");
////        query.setParameter("id", id);
//        int result = query.executeUpdate();
//
//    }
}
