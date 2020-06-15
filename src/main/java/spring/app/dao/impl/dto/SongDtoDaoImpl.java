package spring.app.dao.impl.dto;

import org.springframework.stereotype.Repository;
import spring.app.dao.abstraction.dto.SongDtoDao;
import spring.app.dto.SongDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class SongDtoDaoImpl implements SongDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<SongDto> getAll() {
        List<SongDto> songDtos = entityManager.createQuery(
                "SELECT new spring.app.dto.SongDto(s.id, s.name, s.isApproved, s.author.name, g.name) FROM Song s left JOIN s.author.genres g",
                SongDto.class
        )
                .getResultList();

        return songDtos;
    }

    @Override
    public List<SongDto> listOfSongsByTag(String tag) {
        List<SongDto> songDtos =  entityManager.createQuery(
                "SELECT new spring.app.dto.SongDto(s.id, s.name, s.isApproved, s.author.name, g.name) " +
                        "FROM Song s INNER JOIN s.tags t left JOIN s.author.genres g WHERE t.name = :name", SongDto.class)
                .setParameter("name", tag)
                .getResultList();
        return songDtos;
    }

    @Override
    public SongDto getById(long songId) {
        return entityManager.createQuery("SELECT new spring.app.dto.SongDto(s.id, s.name, s.isApproved, s.author.name, g.name) " +
                "FROM Song s left JOIN s.author.genres g where s.id = :id", SongDto.class)
                .setParameter("id", songId)
                .setMaxResults(1)
                .getResultList()
                .get(0);
    }

    @Override
    public List<SongDto> getAllApprovedDto() {
        List<SongDto> list = entityManager.createQuery("SELECT new spring.app.dto.SongDto(s.id, s.name, s.isApproved, s.author.name, " +
                "g.name) FROM Song s left JOIN s.author.genres g  WHERE s.isApproved = true", SongDto.class)
                .getResultList();
        return list;
    }

    @Override
    public List<SongDto> getSongsOutOfGenreDto(Long genreId) {
        List<SongDto> list = entityManager.createQuery("SELECT new spring.app.dto.SongDto(s.id, s.name, s.isApproved, s.author.name, " +
                "g.name) FROM Song s left JOIN s.author.genres g WHERE g.id <> :id", SongDto.class)
                .setParameter("id", genreId)
                .getResultList();
        return list;
    }

    @Override
    public List<SongDto> getAllWithGenreByGenreIdDto(Long id) {
        List<SongDto> list = entityManager.createQuery("SELECT new spring.app.dto.SongDto(s.id, s.name, s.isApproved, s.author.name, " +
                "g.name) FROM Song s left JOIN s.author.genres g WHERE g.id = :id", SongDto.class)
                .setParameter("id", id)
                .getResultList();
        return list;
    }

}
