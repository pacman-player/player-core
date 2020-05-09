package spring.app.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.SongCompilationDao;
import spring.app.model.Song;
import spring.app.model.SongCompilation;

import javax.persistence.TypedQuery;
import java.util.List;


@Repository
public class SongCompilationDaoImpl extends AbstractDao<Long, SongCompilation> implements SongCompilationDao {

    SongCompilationDaoImpl() {
        super(SongCompilation.class);
    }


    @Override
    public List<Song> getSongCompilationContentById(Long compilationId) {
        TypedQuery<Song> query = entityManager.createQuery(
                "SELECT s FROM Song s JOIN s.songCompilations sc WHERE sc.id = :id ", Song.class);
        query.setParameter("id", compilationId);

        return query.getResultList();
    }

    @Override
    public List<Song> getAvailableContentForCompilation(SongCompilation songCompilation) {
        TypedQuery<Song> query = entityManager.createQuery(
                "SELECT s FROM Song s WHERE s.genre.id = :genreId AND s.isApproved = true AND s NOT IN " +
                        "(SELECT s FROM Song s JOIN s.songCompilations sc WHERE sc.id = :compilationId )", Song.class);
        query.setParameter("genreId", songCompilation.getGenre().getId());
        query.setParameter("compilationId", songCompilation.getId());

        return query.getResultList();
    }

    @Override
    public void removeSongFromSongCompilation(SongCompilation songCompilation, Song song) {
        song.getSongCompilations().remove(songCompilation);
    }

    @Override
    public void addSongToSongCompilation(SongCompilation songCompilation, Song song) {
        songCompilation.getSong().add(song);
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

    @Override
    public List<SongCompilation> getAllWhereSongIsAprove() {
        List<SongCompilation> songCompilationList
                = entityManager.createQuery(
                "SELECT DISTINCT sc FROM SongCompilation sc    JOIN FETCH  sc.song as s where s.isApproved=true ", SongCompilation.class)
                .getResultList();
        return songCompilationList;

    }

    @Override
    public List<SongCompilation> getListSongCompilationsByGenreId(Long id) {
        List<SongCompilation> songCompilationList
                = entityManager.createQuery(
                "SELECT DISTINCT sc FROM SongCompilation sc join sc.song song WHERE sc.genre.id = :id AND song.isApproved = true"
                , SongCompilation.class)
                .setParameter("id", id)
                .getResultList();
        return songCompilationList;
    }






}
