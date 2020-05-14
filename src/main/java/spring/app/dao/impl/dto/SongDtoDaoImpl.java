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
                "SELECT new spring.app.dto.SongDto(s.id, s.name, s.isApproved, s.author.name, s.genre.name) FROM Song s",
                SongDto.class
        )
                .getResultList();

        return songDtos;
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

    @Override
    public List<SongDto> getAllWithGenreByGenreIdDto(Long id) {

        List<SongDto> list = entityManager.createQuery("SELECT new spring.app.dto.SongDto(s.id, s.name, s.isApproved, s.author.name, " +
                "s.genre.name) FROM Song s JOIN s.genre g WHERE g.genre_id = :id", SongDto.class)
                .setParameter("id", id)
                .getResultList();
        return list;
    }

    @Override
    public List<SongDto> getAllApprovedDto() {
        List<SongDto> list = entityManager.createQuery("SELECT new spring.app.dto.SongDto(s.id, s.name, s.isApproved, s.author.name, " +
                "s.genre.name) FROM Song s  WHERE s.isApproved = true", SongDto.class)
                .getResultList();
        return list;

//        String genericClassName = Song.class.toGenericString();
//        genericClassName = genericClassName.substring(genericClassName.lastIndexOf('.') + 1);
//        String hql = "FROM " + genericClassName + " as c WHERE c.isApproved = true";
//        TypedQuery<SongDto> query = entityManager.createQuery(hql, SongDto.class);
//        return query.getResultList();

    }


}
