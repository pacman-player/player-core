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
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional(readOnly = true)
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
    @Override
    public boolean isExistCompanyByName(String name) {
        long count = (long)entityManager.createQuery(
                "select count(c) from Company c WHERE c.name=:name")
                .setParameter("name", name)
                .getSingleResult();
        return count > 0;
    }

    @Override
    @Fetch(FetchMode.JOIN)
    public Company getCompanyWithEntityBanned(long id) {
        try {
            return entityManager.
                    createQuery("FROM Company c WHERE id = :id",
                            Company.class).
                    setParameter("id", id).
                    getSingleResult();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}