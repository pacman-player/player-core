package spring.app.dao.impl.dto;

import org.springframework.stereotype.Repository;
import spring.app.dao.abstraction.dto.SongDtoDao;
import spring.app.dto.SongDto;
import spring.app.model.Song;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class SongDtoDaoImpl implements SongDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<SongDto> getAll() {
        List<SongDto> songDtos = entityManager.createQuery(
                "SELECT new spring.app.dto.SongDto(s.id, s.name, s.isApproved, s.author.name, s.genre.name) FROM Song s",
                SongDto.class
        )
                .getResultList();

        return songDtos;
    }

    @Override
    public List<SongDto> listOfSongsByName(String name) {
        String hqlFormat = "SELECT new %s(song.id, song.name, song.isApproved, song.author.name, song.genre.name) FROM %s song " +
                           " WHERE song.name LIKE :name";
        String hql = String.format(hqlFormat, SongDto.class.getName(), Song.class.getSimpleName());

        TypedQuery<SongDto> query = entityManager.createQuery(hql, SongDto.class)
                                                 .setParameter("name", "%" + name + "%");

        return query.getResultList();
    }

    @Override
    public List<SongDto> listOfSongsByTag(String tag) {
        List<SongDto> songDtos =  entityManager.createQuery(
                "SELECT new spring.app.dto.SongDto(s.id, s.name, s.isApproved, s.author.name, s.genre.name) " +
                        "FROM Song s INNER JOIN s.tags t WHERE t.name = :name", SongDto.class)
                .setParameter("name", tag)
                .getResultList();
        return songDtos;
    }
}
