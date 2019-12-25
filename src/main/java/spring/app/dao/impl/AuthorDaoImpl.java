package spring.app.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.AuthorDao;
import spring.app.model.Author;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

@Repository
@Transactional
public class AuthorDaoImpl extends AbstractDao<Long, Author> implements AuthorDao {
    public AuthorDaoImpl() {
        super(Author.class);
    }

    @Override
    public Author getByName(String name) {
        TypedQuery<Author> query = entityManager.createQuery("FROM Author WHERE name = :name", Author.class);
        query.setParameter("name", name);
        Author author;
        try {
            author = query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return author;
    }
}