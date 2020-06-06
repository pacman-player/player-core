package spring.app.dao.impl.dto;

import org.springframework.stereotype.Repository;
import spring.app.dao.abstraction.dto.TagDtoDao;
import spring.app.dto.TagDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class TagDtoDaoImpl implements TagDtoDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<TagDto> getAll(int pageSize, int pageNo) {
        return entityManager.createQuery(
                "SELECT new spring.app.dto.TagDto(t.id, t.name) FROM Tag t",
                TagDto.class
        )
                .setMaxResults(pageSize)
                .setFirstResult(pageNo * pageSize)
                .getResultList();
    }

    @Override
    public Long getRowsCount(){
        return entityManager.createQuery(
                "SELECT count(t) FROM Tag t", Long.class
        )
                .getSingleResult();
    }
}
