package spring.app.dao.impl;

import org.springframework.stereotype.Repository;
import spring.app.dao.abstraction.TagDao;
import spring.app.model.Tag;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.Set;

@Repository
public class TagDaoImpl extends AbstractDao<Long, Tag> implements TagDao {

    TagDaoImpl() {
        super(Tag.class);
    }

    @Override
    public Set<Tag> getByNames(Set<String> names) {
        TypedQuery<Tag> query = entityManager.createQuery("FROM Tag WHERE name IN :names", Tag.class);
        query.setParameter("names", names);
        Set<Tag> result = new HashSet<>(query.getResultList());
        return result;
    }

    @Override
    public boolean isExistByName(String name) {
        Query query = entityManager.createQuery("SELECT count(*) FROM Tag WHERE name = :name");
        query.setParameter("name", name);
        long count = (long) query.getSingleResult();
        return count > 0;
    }
}
