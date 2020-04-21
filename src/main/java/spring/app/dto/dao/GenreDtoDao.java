package spring.app.dto.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.controller.restController.SongRestController;
import spring.app.dto.GenreDto;
import spring.app.model.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GenreDtoDao {
    @PersistenceContext
    private EntityManager entityManager;

    private final static Logger LOGGER = LoggerFactory.getLogger(GenreDtoDao.class);
    public List<GenreDto> getAll() {
        return entityManager.createQuery(
                "SELECT new spring.app.dto.GenreDto(" +
                        "a.id, " +
                        "a.name, " +
                        "a.isApproved" +
                        ") FROM Genre a",
                GenreDto.class
        )
                .getResultList();
    }
    public boolean isExistByName(String name) {
        Query query = entityManager.createQuery(
                "SELECT  count (*)  FROM " + Genre.class.getName() + " a WHERE a.name = :name"
        );
        query.setParameter("name", name);

        if (query.getResultList().get(0).equals(0L)) {
            return false;
        } else {
            return true;
        }
    }

}
