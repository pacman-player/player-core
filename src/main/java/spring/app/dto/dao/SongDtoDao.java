package spring.app.dto.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dto.SongDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class SongDtoDao {
    @PersistenceContext
    private EntityManager entityManager;

    public List<SongDto> getAll() {
        List<SongDto> songDtos = entityManager.createQuery(
                "SELECT new spring.app.dto.SongDto(" +
                        "t.id, " +
                        "t.name, " +
                        "t.isApproved, "+
                        "t.author.name, "+
                        "t.genre.name "+
                        ") FROM Song t",
                SongDto.class
        )
                .getResultList();

        return songDtos;
    }
}
