package spring.app.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.AuthorDao;
import spring.app.model.Author;

@Repository
@Transactional
public class AuthorDaoImpl extends AbstractDao<Long, Author> implements AuthorDao {
    AuthorDaoImpl() {
        super(Author.class);
    }
}