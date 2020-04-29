package spring.app.dao.impl.dto;

import org.springframework.stereotype.Repository;
import spring.app.dao.abstraction.dto.RoleDtoDao;
import spring.app.dto.RoleDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDtoDaoImpl implements RoleDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<RoleDto> getAllRoles() {
        return entityManager.createQuery(
                "SELECT new spring.app.dto.RoleDto(r.id, r.name ) FROM Role r",
                RoleDto.class
        )
                .getResultList();
    }
}
