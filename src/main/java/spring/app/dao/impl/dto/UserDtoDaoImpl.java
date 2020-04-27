package spring.app.dao.impl.dto;

import org.hibernate.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;
import spring.app.dao.abstraction.dto.UserDtoDao;
import spring.app.dto.UserDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;

@Repository
public class UserDtoDaoImpl implements UserDtoDao {

    @PersistenceContext
    EntityManager entityManager;

    //TODO: replace from DtoDao to EntityDao
    @Override
    public boolean isExistUserByEmail(String email) {
        TypedQuery<Boolean> booleanQuery = entityManager.createQuery(
                "SELECT CASE WHEN (count(*) > 0)  THEN true ELSE false END FROM User u WHERE u.email = :email",
                Boolean.class);
        booleanQuery.setParameter("email", email);
        boolean exists = booleanQuery.getSingleResult();
        return exists;
    }

    //TODO: replace from DtoDao to EntityDao
    @Override
    public boolean isExistUserByLogin(String login) {
        TypedQuery<Boolean> booleanQuery = entityManager.createQuery(
                "SELECT CASE WHEN (count(*) > 0)  THEN true ELSE false END FROM User u WHERE u.login = :login",
                Boolean.class);
        booleanQuery.setParameter("login", login);
        boolean exists = booleanQuery.getSingleResult();
        return exists;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<UserDto> allUserDtos = entityManager.createQuery(
                "SELECT u.id, u.login, u.email, u.password, u.enabled, r.name " +
                        "FROM User u LEFT JOIN u.roles r"
        )
                .unwrap(Query.class)
                .setResultTransformer(new UserDtoTransformer())
                .list();

        return allUserDtos;
    }

    @Override
    public UserDto getById(Long id) {
        UserDto userDto = (UserDto) entityManager.createQuery(
                "SELECT u.id, u.login, u.email, u.password, u.enabled, r.name " +
                        "FROM User u LEFT JOIN u.roles r where u.id = :userId"
        )
                .unwrap(Query.class)
                .setParameter("userId", id)
                .setResultTransformer(new UserDtoTransformer())
                .uniqueResult();

        return userDto;
    }

    private class UserDtoTransformer implements ResultTransformer {

        private List<UserDto> roots = new ArrayList<>();
        private Map<Long, Set<String>> rolesMap = new HashMap<>();

        @Override
        public Object transformTuple(Object[] tuple, String[] aliacess) {
            long userId = (long) tuple[0];
            String login =  (String) tuple[1];
            String email = (String) tuple[2];
            String password = (String) tuple[3];
            Boolean enabled = (Boolean) tuple[4];
            String roleName = (String) tuple[5];

            UserDto userDto = new UserDto(userId, login, email, password, enabled);

            if (!rolesMap.containsKey(userId)) {
                roots.add(userDto);
                rolesMap.put(userId, new HashSet<>());
            }

            rolesMap.get(userId).add(roleName);
            return userDto;
        }

        @Override
        public List transformList(List list) {
            for (UserDto userDto : roots) {
                Set<String> roles = rolesMap.get(userDto.getId());
                userDto.setRoles(roles);
            }

            return roots;
        }
    }
}
