package spring.app.dao.impl.dto;

import org.hibernate.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.dto.AuthorDtoDao;
import spring.app.dto.AuthorDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Transactional(readOnly = true)
public class AuthorDtoDaoImpl implements AuthorDtoDao {

    @PersistenceContext
    EntityManager entityManager;

    public List<AuthorDto> getAllAuthors() {
        List<AuthorDto> authorDto = entityManager.createQuery(
                "SELECT a.id, a.name, a.createdAt, a.isApproved, g.name " +
                        "FROM Author a LEFT JOIN a.genres g"
        )
                .unwrap(Query.class)
                .setResultTransformer(new AuthorDtoTransformer())
                .list();

        return authorDto;
    }

    private static class AuthorDtoTransformer implements ResultTransformer {

        private List<AuthorDto> roots = new ArrayList<>();
        private Map<Long, List<String>> genresMap = new HashMap<>();

        @Override
        public Object transformTuple(Object[] tuple, String[] aliaces) {
            long authorId = (long) tuple[0];
            String name =  (String) tuple[1];
            Timestamp createdAt = (Timestamp) tuple[2];
            Boolean approved = (Boolean) tuple[3];
            String genre = (String) tuple[4];

            AuthorDto authorDto = new AuthorDto(authorId, name, createdAt, approved);

            if (!genresMap.containsKey(authorId)) {
                roots.add(authorDto);
                genresMap.put(authorId, new ArrayList<>());
            }

            genresMap.get(authorId).add(genre);
            return authorDto;
        }

        @Override
        public List transformList(List list) {
            for (AuthorDto authorDto : roots) {
                List<String> genres = genresMap.get(authorDto.getId());
                authorDto.setGenres(genres.isEmpty() ? new String[]{""} : genres.stream().toArray(String[]::new));
            }

            return roots;
        }
    }
}
