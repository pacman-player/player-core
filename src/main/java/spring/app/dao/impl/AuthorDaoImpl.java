package spring.app.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.AuthorDao;
import spring.app.model.Author;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.sql.Timestamp;
import java.util.List;

@Repository
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

    @Override
    public List<Author> getByCreatedDateRange(Timestamp dateFrom, Timestamp dateTo) {
        return entityManager
                .createQuery("FROM Author a WHERE a.createdAt >= :dateFrom AND a.createdAt <= :dateTo ORDER BY a.createdAt", Author.class)
                .setParameter("dateFrom", dateFrom)
                .setParameter("dateTo", dateTo).getResultList();
    }

    public List<Author> getApprovedPage(int pageNumber, int pageSize) {
        String genericClassName = Author.class.toGenericString();
        genericClassName = genericClassName.substring(genericClassName.lastIndexOf('.') + 1);
        String jql = "FROM " + genericClassName + " as c WHERE c.isApproved = true";
        TypedQuery<Author> query = entityManager.createQuery(jql, Author.class);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    public int getLastApprovedPageNumber(int pageSize) {
        String genericClassName = Author.class.toGenericString();
        genericClassName = genericClassName.substring(genericClassName.lastIndexOf('.') + 1);
        String jql = "Select count(*) from " + genericClassName + " WHERE isApproved = true";
        Query query = entityManager.createQuery(jql);
        long count = (long) query.getSingleResult();
        int lastPageNumber = (int) ((count / pageSize) + 1);
        return lastPageNumber;
    }

    @Override
    public boolean isExist(String name) {
        TypedQuery<Author> query = entityManager.createQuery("FROM Author WHERE name = :name", Author.class);
        query.setParameter("name", name);
        List list = query.getResultList();
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }
}