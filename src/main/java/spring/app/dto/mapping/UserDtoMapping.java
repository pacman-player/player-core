package spring.app.dto.mapping;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dto.AuthorDto;
import spring.app.dto.GenreDto;
import spring.app.dto.RoleDto;
import spring.app.dto.UserDto;
import spring.app.model.Author;
import spring.app.model.Genre;
import spring.app.model.Role;
import spring.app.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Repository
public class UserDtoMapping {
    @PersistenceContext
    EntityManager entityManager;

    public List<UserDto> getAllUsers() {
        List<UserDto> userDtos = entityManager.createQuery(
                "SELECT new spring.app.dto.UserDto(" +
                        "t.id, " +
                        "t.login, " +
                        "t.email, " +
                        "t.password " +
                        ") FROM " + User.class.getName() + " t",
                UserDto.class
        )
                .getResultList();

        List<Long> ids = userDtos.stream().mapToLong(UserDto::getId).boxed().collect(Collectors.toList());

        List<RoleDto> roles = entityManager.createQuery(
                "SELECT new spring.app.dto.RoleDto (a.id, a.name) "+
                        "FROM  Role a WHERE a.id IN (:ids)",
                RoleDto.class
        )
                .setParameter("ids", ids)
                .getResultList();

//        userDtos.forEach(tdwa -> tdwa.setRoles(
//                roles.stream().filter(a -> a.getId() == tdwa.getId()).collect(Collectors.toList())
//        ));


        return userDtos;
    }
}

