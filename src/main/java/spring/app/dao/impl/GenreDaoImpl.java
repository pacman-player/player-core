package spring.app.dao.impl;

import org.springframework.stereotype.Repository;
import spring.app.dao.abstraction.GenreDao;
import spring.app.model.Company;
import spring.app.model.Genre;
import spring.app.model.OrgType;
import spring.app.model.Song;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.sql.Timestamp;
import java.util.List;

@Repository
//  @Transactional(readOnly = true) это наверное надо удалить, тк транзакции в сервисе прописаны
public class GenreDaoImpl extends AbstractDao<Long, Genre> implements GenreDao {
    GenreDaoImpl() {
        super(Genre.class);
    }


    @Override
    public void deleteById(Long id) {
        Query query = entityManager.createQuery("DELETE FROM Genre WHERE id = " + id);
        query.executeUpdate();
    }


    @Override
    public Genre getByName(String name) {
        TypedQuery<Genre> query = entityManager.createQuery("SELECT u FROM Genre u WHERE u.name = :name", Genre.class);
        query.setParameter("name", name);
        Genre genre;
        try {
            genre = query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return genre;
    }


    @Override
    public List<Genre> getByCreatedDateRange(Timestamp dateFrom, Timestamp dateTo) {
        return entityManager
                .createQuery("FROM Genre g WHERE g.createdAt >= :dateFrom AND g.createdAt <= :dateTo ORDER BY g.createdAt", Genre.class)
                .setParameter("dateFrom", dateFrom)
                .setParameter("dateTo", dateTo)
                .getResultList();
    }

    public List<Genre> getAllApproved() {
        String genericClassName = Genre.class.toGenericString();
        genericClassName = genericClassName.substring(genericClassName.lastIndexOf('.') + 1);
        String hql = "FROM " + genericClassName + " as c WHERE c.isApproved = true";
        TypedQuery<Genre> query = entityManager.createQuery(hql, Genre.class);
        return query.getResultList();
    }

    @Override
    public List<Song> getSongsByGenre(Genre genre) {
        TypedQuery<Song> query = entityManager.createQuery("SELECT u FROM Song u WHERE u.genre = :genre", Song.class);
        query.setParameter("genre", genre);
        return query.getResultList();
    }

    @Override
    public void deleteReferenceFromOrgTypeByGenre(Genre genre) {
        TypedQuery<OrgType> queryOrgType = (TypedQuery<OrgType>) entityManager.createNativeQuery("DELETE FROM org_type_on_related_genre WHERE genre_id = " + genre.getId());
        queryOrgType.executeUpdate();
    }

    @Override
    public void deleteReferenceFromCompanyByGenre(Genre genre) {
        TypedQuery<Company> queryOrgType = (TypedQuery<Company>) entityManager.createNativeQuery("DELETE FROM company_on_banned_genre WHERE genre_id =" + genre.getId());
        queryOrgType.executeUpdate();
    }

    @Override
    public void flush() {
        entityManager.flush();
    }
}