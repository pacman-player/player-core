package spring.app.dao.impl.dto;

import org.hibernate.SQLQuery;
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
        String hql = "SELECT new spring.app.dto.GenreDto(genre.id, genre.name, genre.isApproved, g.keywords) FROM Genre genre";

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

    @Override
    @SuppressWarnings("unchecked")
    public List<String> getGenresByKeywords(String foundGenres) {
        String ftsQuery = "SELECT g.name FROM genres g " +
                "WHERE to_tsvector(:keys) @@ to_tsquery(g.keywords)";
        return entityManager.createNativeQuery(ftsQuery)
                .setParameter("keys", foundGenres)
                .unwrap(SQLQuery.class)
                .list();
    }

    public List<GenreDto> getAllApprovedDto() {
        return entityManager.createQuery("SELECT new spring.app.dto.GenreDto(g.id, g.name, g.isApproved) FROM Genre g WHERE g.isApproved = true ",
                GenreDto.class)
                .getResultList();
    }
}
