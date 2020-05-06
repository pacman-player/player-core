package spring.app.dao.impl.dto;

import org.springframework.stereotype.Repository;
import spring.app.dao.abstraction.dto.OrgTypeDtoDao;
import spring.app.dto.OrgTypeDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class OrgTypeDtoDaoImpl implements OrgTypeDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<OrgTypeDto> getAll() {
        return entityManager.createQuery("SELECT new spring.app.dto.OrgTypeDto(o.id, o.name) FROM OrgType o",
                OrgTypeDto.class).getResultList();
    }
}
