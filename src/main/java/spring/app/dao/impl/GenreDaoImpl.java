package spring.app.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.GenreDao;
import spring.app.model.*;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.sql.Timestamp;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class GenreDaoImpl extends AbstractDao<Long, Genre> implements GenreDao {
    GenreDaoImpl() {
        super(Genre.class);
    }

    @Override
    public void deleteById(Long id) {
        Long notDefinedGenreId = entityManager
                .createQuery("SELECT g.id FROM Genre g WHERE g.name = :name", Long.class)
                .setParameter("name", "not defined")
                .getSingleResult();

        TypedQuery<Company> queryCompany = (TypedQuery<Company>) entityManager.createNativeQuery("DELETE FROM company_on_banned_genre WHERE genre_id = :id");
        queryCompany.setParameter("id", id);
        queryCompany.executeUpdate();

        TypedQuery<OrgType> queryOrgType = (TypedQuery<OrgType>) entityManager.createNativeQuery("DELETE FROM org_type_on_related_genre WHERE genre_id = :id");
        queryOrgType.setParameter("id", id);
        queryOrgType.executeUpdate();

        TypedQuery<Author> queryAuthor = (TypedQuery<Author>) entityManager.createNativeQuery("UPDATE author_on_genre SET genre_id = :genreId WHERE genre_id = :id");
        queryAuthor.setParameter("genreId", notDefinedGenreId);
        queryAuthor.setParameter("id", id);
        queryAuthor.executeUpdate();

        TypedQuery<Song> querySong = (TypedQuery<Song>) entityManager.createQuery("UPDATE Song SET genre_id = :genreId WHERE genre_id = :id");
        querySong.setParameter("genreId", notDefinedGenreId);
        querySong.setParameter("id", id);
        querySong.executeUpdate();

        TypedQuery<SongCompilation> querySongCompilation = (TypedQuery<SongCompilation>) entityManager.createQuery("UPDATE SongCompilation SET genre_id = :genreId WHERE genre_id = :id");
        querySongCompilation.setParameter("genreId", notDefinedGenreId);
        querySongCompilation.setParameter("id", id);
        querySongCompilation.executeUpdate();

        super.deleteById(id);
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
}