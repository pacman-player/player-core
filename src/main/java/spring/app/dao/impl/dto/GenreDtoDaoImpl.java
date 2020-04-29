package spring.app.dao.impl.dto;

import org.springframework.stereotype.Repository;
import spring.app.dao.abstraction.dto.GenreDtoDao;
import spring.app.dto.GenreDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class GenreDtoDaoImpl implements GenreDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<GenreDto> getAll() {
        return entityManager.createQuery(
                "SELECT new spring.app.dto.GenreDto(g.id, g.name, g.isApproved) FROM Genre g",
                GenreDto.class
        )
                .getResultList();
    }

    //TODO: replace from DtoDao to EntityDao
    @Override
    public boolean isExistByName(String name) {
        Query query = entityManager.createQuery(
                "SELECT  count (*)  FROM Genre g WHERE g.name = :name"
        );
        query.setParameter("name", name);

        if (query.getResultList().get(0).equals(0L)) {
            return false;
        } else {
            return true;
        }
    }

}
