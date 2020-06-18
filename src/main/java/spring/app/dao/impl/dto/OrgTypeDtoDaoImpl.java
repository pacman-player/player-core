package spring.app.dao.impl.dto;

import org.hibernate.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;
import spring.app.dao.abstraction.dto.OrgTypeDtoDao;
import spring.app.dto.OrgTypeDto;
import spring.app.model.OrgType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrgTypeDtoDaoImpl implements OrgTypeDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public List<OrgTypeDto> getAll() {
        String query = "SELECT ot.id, ot.name, g.name FROM OrgType ot LEFT JOIN ot.genres g";

        return entityManager.createQuery(query)
                            .unwrap(Query.class)
                            .setResultTransformer(new OrgTypeDtoTransformer())
                            .list();
    }


    private static class OrgTypeDtoTransformer implements ResultTransformer {

        private List<OrgTypeDto> roots = new ArrayList<>();
        private Map<Long, List<String>> genresMap = new HashMap<>();

        @Override
        public Object transformTuple(Object[] tuple, String[] aliaces) {
            long id = (long) tuple[0];
            String name = (String) tuple[1];
            String genre = (String) tuple[2];

            OrgTypeDto orgTypeDto = new OrgTypeDto(id, name);

            if (!genresMap.containsKey(id)) {
                roots.add(orgTypeDto);
                genresMap.put(id, new ArrayList<>());
            }
            if (genre != null) {
                genresMap.get(id).add(genre);
            }
            return orgTypeDto;
        }

        @Override
        public List<OrgTypeDto> transformList(List list) {
            for (OrgTypeDto orgTypeDto : roots) {
                List<String> genres = genresMap.get(orgTypeDto.getId());
                orgTypeDto.setGenres(genres);
            }
            return roots;
        }
    }
}