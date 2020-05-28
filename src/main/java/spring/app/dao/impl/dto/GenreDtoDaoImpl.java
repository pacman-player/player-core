package spring.app.dao.impl.dto;

import org.springframework.stereotype.Repository;
import spring.app.dao.abstraction.dto.GenreDtoDao;
import spring.app.dto.GenreDto;
import spring.app.model.Genre;

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
        String hql = "SELECT new spring.app.dto.GenreDto(genre.id, genre.name, genre.isApproved) FROM Genre genre";

        return entityManager.createQuery(hql, GenreDto.class)
                            .getResultList();
    }

    //TODO: replace from DtoDao to EntityDao
    @Override
    public boolean isExistByName(String name) {
        long count = entityManager.createQuery("SELECT count(g.id) FROM Genre g WHERE g.name = :name", Long.class)
                                  .setParameter("name", name)
                                  .getSingleResult();

        return count != 0;
    }

}
