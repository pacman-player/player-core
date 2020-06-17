package spring.app.dao.impl.dto;

import org.springframework.stereotype.Repository;
import spring.app.dao.abstraction.dto.RoleDtoDao;
import spring.app.dto.RoleDto;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDtoDaoImpl implements RoleDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<RoleDto> getAllRolesDto() {
        return entityManager.createQuery(
                "SELECT new spring.app.dto.RoleDto(r.id, r.name ) FROM Role r",
                RoleDto.class
        )
                .getResultList();
    }

    @Override
    public RoleDto getRoleDtoByName(String roleName) {
        RoleDto roleDto;
        try {
            roleDto = entityManager.createQuery(
                    "SELECT new spring.app.dto.RoleDto(r.id, r.name ) FROM Role r WHERE r.name = :name",
                    RoleDto.class
            )
                    .setParameter("name", roleName)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return roleDto;
    }

    @Override
    public RoleDto getRoleDtoById(Long id) {
        RoleDto roleDto;
        try {
            roleDto = entityManager.createQuery(
                    "SELECT new spring.app.dto.RoleDto(r.id, r.name ) FROM Role r WHERE r.id = :id",
                    RoleDto.class
            )
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return roleDto;
    }

}
