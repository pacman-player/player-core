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
        return entityManager.createQuery("SELECT o.id, o.name, o.isDefault, g.name FROM OrgType o LEFT JOIN o.genres g")
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
            boolean isDefault = (boolean) tuple[2];
            String genre = (String) tuple[3];

            OrgTypeDto orgTypeDto = new OrgTypeDto(id, name, isDefault);

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