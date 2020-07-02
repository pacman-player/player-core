package spring.app.dao.impl;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;
import spring.app.dao.abstraction.GenreDao;
import spring.app.model.Company;
import spring.app.model.Genre;
import spring.app.model.OrgType;
import spring.app.model.Song;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Repository
@Transactional
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
        initLazyFields(genre);
        return genre;
    }

    @Override
    public List<Genre> getByCreatedDateRange(Timestamp dateFrom, Timestamp dateTo) {
        List<Genre> list = entityManager
                .createQuery("FROM Genre g WHERE g.createdAt >= :dateFrom AND g.createdAt <= :dateTo ORDER BY g.createdAt", Genre.class)
                .setParameter("dateFrom", dateFrom)
                .setParameter("dateTo", dateTo)
                .getResultList();

        for (Genre g :
                list) {
            initLazyFields(g);
        }
        return list;
    }

    public List<Genre> getAllApproved() {
        String genericClassName = Genre.class.toGenericString();
        genericClassName = genericClassName.substring(genericClassName.lastIndexOf('.') + 1);
        String hql = "FROM " + genericClassName + " as c WHERE c.isApproved = true";
        TypedQuery<Genre> query = entityManager.createQuery(hql, Genre.class);
        List<Genre> list = query.getResultList();
        for (Genre g :
                list) {
            initLazyFields(g);
        }
        return list;
    }

    @Override
    public List<Song> getSongsByGenre(Genre genre) {
        TypedQuery<Song> query = entityManager.createQuery("SELECT s FROM Song s left JOIN s.author.genres g WHERE g.name = :genre", Song.class);
        query.setParameter("genre", genre);
        List<Song> list = query.getResultList();
        for (Song s :
                list) {
            Hibernate.initialize(s.getSongCompilations());
            Hibernate.initialize(s.getTags());
        }
        return list;
    }

    @Override
    public long getDefaultGenreId(){
        long id = (long) entityManager.createQuery("SELECT id FROM Genre WHERE isDefault = true").getSingleResult();
        return id;
    }

    @Override
    public void deleteReferenceFromCompanyByGenre(Long id) {
        TypedQuery<Company> queryOrgType = (TypedQuery<Company>) entityManager.createNativeQuery("DELETE FROM company_on_banned_genre WHERE genre_id = :id");
        queryOrgType.setParameter("id", id);
        queryOrgType.executeUpdate();
    }

    @Override
    public void deleteReferenceFromOrgTypeByGenre(Long id) {
        TypedQuery<OrgType> queryOrgType = (TypedQuery<OrgType>) entityManager.createNativeQuery("DELETE FROM org_type_on_related_genre WHERE genre_id = :id");
        queryOrgType.setParameter("id", id);
        queryOrgType.executeUpdate();
    }

    @Override
    public void deleteReferenceFromAuthorByGenre(Long id){
       entityManager.createNativeQuery("DELETE FROM author_on_genre WHERE genre_id = :id").setParameter("id", id).executeUpdate();
    }

    @Override
    public void setDefaultGenre(Long id){
        entityManager.createQuery("UPDATE Genre g SET g.isDefault = CASE WHEN g.id=:id THEN true ELSE false END").setParameter("id", id).executeUpdate();
    }

    public void setDefaultGenreToOrgType(Long deleteGenreId, Long defaultGenreId){
        Query query = entityManager.createNativeQuery("UPDATE org_type_on_related_genre SET genre_id = :defaultGenreId WHERE genre_id = :deleteGenreId");
        query.setParameter("deleteGenreId", deleteGenreId);
        query.setParameter("defaultGenreId", defaultGenreId);
        query.executeUpdate();
    }

    @Override
    public void setDefaultGenreToAuthor(Long deleteGenreId, Long defaultGenreId){
        Query query = entityManager.createNativeQuery("UPDATE author_on_genre set genre_id = :defaultGenreId WHERE genre_id = :deleteGenreId");
        query.setParameter("deleteGenreId", deleteGenreId);
        query.setParameter("defaultGenreId", defaultGenreId);
        query.executeUpdate();
    }

    @Override
    public BigInteger countOfGenresInOrgType(Long deletedGenreId){
        return (BigInteger) entityManager.createNativeQuery("WITH dup_org_type AS (SELECT org_type_id FROM org_type_on_related_genre GROUP BY org_type_id HAVING COUNT(0) > 1) SELECT COUNT(0) FROM org_type_on_related_genre otrg, dup_org_type dup WHERE otrg.genre_id = :deletedGenreId AND otrg.org_type_id = dup.org_type_id").setParameter("deletedGenreId", deletedGenreId).getSingleResult();
    }

    @Override
    public BigInteger countOfGenresInAuthor(Long deletedGenreId){
        return (BigInteger) entityManager.createNativeQuery("WITH dup_author AS (SELECT author_id FROM author_on_genre GROUP BY author_id HAVING COUNT(0) > 1) SELECT COUNT(0) FROM author_on_genre aog, dup_author dup WHERE aog.genre_id = :deletedGenreId AND aog.author_id = dup.author_id").setParameter("deletedGenreId", deletedGenreId).getSingleResult();
    }

    private void initLazyFields(Genre genre) {
        Hibernate.initialize(genre.getAuthors());
    }
}