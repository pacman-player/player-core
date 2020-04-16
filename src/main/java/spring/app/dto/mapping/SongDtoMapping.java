package spring.app.dto.mapping;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dto.AuthorDto;
import spring.app.dto.SongDto;
import spring.app.model.Author;
import spring.app.model.Song;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Transactional
@Repository
public class SongDtoMapping {
    @PersistenceContext
    EntityManager entityManager;

    public List<SongDto> getAll() {
        List<SongDto> songDtos = entityManager.createQuery(
                "SELECT new spring.app.dto.SongDto(" +
                        "t.id, " +
                        "t.name, " +
                        "t.isApproved, "+
                        "t.author.name, "+
                        "t.genre.name "+
                        ") FROM "+ Song.class.getName()+" t",
                SongDto.class
        )
                .getResultList();

        return songDtos;
    }
}
