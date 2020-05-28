package spring.app.dao.abstraction;

import spring.app.model.Tag;

import java.util.Set;

public interface TagDao extends GenericDao<Long, Tag> {

    Set<Tag> getByNames(Set<String> names);

    boolean isExistByName(String name);

}
