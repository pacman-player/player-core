package spring.app.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.GenreDao;
import spring.app.model.*;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

@Repository
@Transactional
public class GenreDaoImpl extends AbstractDao<Long, Genre> implements GenreDao {
    GenreDaoImpl() {
        super(Genre.class);
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
    public void deleteById(Long id) {
        TypedQuery<SongCompilation> query = (TypedQuery<SongCompilation>) entityManager.createQuery("UPDATE SongCompilation SET genre_id = null WHERE genre_id = :id");
        query.setParameter("id", id);
        query.executeUpdate();

        TypedQuery<Company> queryCompany = (TypedQuery<Company>) entityManager.createNativeQuery("DELETE FROM company_on_banned_genre WHERE genre_id = :id");
        queryCompany.setParameter("id", id);
        queryCompany.executeUpdate();

        TypedQuery<OrgType> queryOrgType = (TypedQuery<OrgType>) entityManager.createNativeQuery("DELETE FROM org_type_on_related_genre WHERE genre_id = :id");
        queryOrgType.setParameter("id", id);
        queryOrgType.executeUpdate();

        TypedQuery<Author> queryAuthor = (TypedQuery<Author>) entityManager.createNativeQuery("DELETE FROM author_on_genre WHERE genre_id = :id");
        queryAuthor.setParameter("id", id);
        queryAuthor.executeUpdate();

        super.deleteById(id);
    }
}