package spring.app.dao.impl.dto;

import org.springframework.stereotype.Repository;
import spring.app.dao.abstraction.dto.SongQueueDtoDao;
import spring.app.dto.SongQueueDto;
import spring.app.model.Company;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class SongsQueueDtoDaoImpl implements SongQueueDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<SongQueueDto> getQueuesByCompanyId(Company id) {

        return entityManager.createQuery("SELECT new spring.app.dto.SongQueueDto(s.id, s.position, s.song, s.company)  FROM SongQueue s WHERE s.company = :companyId",
                SongQueueDto.class)
                .setParameter("companyId", id)
                .getResultList();
    }
}
