package spring.app.dto.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dto.RoleDto;
import spring.app.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDtoDao {
    @PersistenceContext
    private EntityManager entityManager;

    public List<RoleDto> getAllRoles() {
        return entityManager.createQuery(
                "SELECT new spring.app.dto.RoleDto(" +
                        "a.id, " +
                        "a.name " +
                        ") FROM " + Role.class.getName() + " a",
                RoleDto.class
        )
                .getResultList();
    }
}
