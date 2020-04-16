package spring.app.dto.mapping;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dto.MessageDto;
import spring.app.dto.RoleDto;
import spring.app.model.Message;
import spring.app.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Transactional
@Repository
public class RoleDtoMapping {
    @PersistenceContext
    EntityManager entityManager;

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
