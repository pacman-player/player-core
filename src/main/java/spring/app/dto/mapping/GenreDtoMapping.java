package spring.app.dto.mapping;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dto.GenreDto;
import spring.app.model.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Transactional
@Repository
public class GenreDtoMapping {
    @PersistenceContext
    EntityManager entityManager;

    public List<GenreDto> getAll() {
        return entityManager.createQuery(
                "SELECT new spring.app.dto.GenreDto(" +
                        "a.id, " +
                        "a.name, " +
                        "a.isApproved" +
                        ") FROM " + Genre.class.getName() + " a",
                GenreDto.class
        )
                .getResultList();
    }


}
