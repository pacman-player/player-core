package spring.app.dao.impl;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.CompanyDao;
import spring.app.model.Company;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class CompanyDaoImpl extends AbstractDao<Long, Company> implements CompanyDao {

    @PersistenceContext
    EntityManager entityManager;

    CompanyDaoImpl() {
        super(Company.class);
    }

    @Override
    public Company getCompanyByCompanyName(String companyName) {
        Company company;
        try {
            company = entityManager
                    .createQuery("FROM Company WHERE name = :name", Company.class)
                    .setParameter("name", companyName)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return company;
    }

    /**
     * Вылает ошибка на получении bannedSong и bannedAuthor, потому у компании нет заблокированных сущностей
     */
//    @Override
//    public Company getCompanyWithEntityBanned(Long id) {
//        try {
//            Company company = entityManager.
//                    createQuery("SELECT c FROM Company c " +
//                            "JOIN FETCH c.bannedGenre " +
//                            "JOIN FETCH c.bannedSong " +
//                            "JOIN FETCH c.bannedAuthor " +
//                            "WHERE c.id = :id", Company.class).
//                    setParameter("id", id).
//                    getSingleResult();
//
//            return company;
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return null;
//        }
//    }
}