package spring.app.dao.abstraction;

import spring.app.model.Author;

import java.util.List;

public interface AuthorDao extends GenericDao<Long, Author> {
    Author getByName(String name);

}
