package spring.app.dao.abstraction;

import spring.app.model.Author;
import spring.app.model.Role;

public interface AuthorDao extends GenericDao<Long, Author> {
    Author getByName(String name);
}
