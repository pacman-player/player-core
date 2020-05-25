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
        String hqlFormat = "SELECT %s(genre.id, genre.name, genre.isApproved) FROM %s genre";
        String hql = String.format(hqlFormat, GenreDto.class.getName(), Genre.class.getSimpleName());

        return entityManager.createQuery(hql, GenreDto.class)
                            .getResultList();
    }

    //TODO: replace from DtoDao to EntityDao
    @Override
    public boolean isExistByName(String name) {
        Query query = entityManager.createQuery("SELECT  count (*)  FROM Genre g WHERE g.name = :name")
                                   .setParameter("name", name);

        return !query.getResultList().get(0).equals(0L);
    }

}
