package spring.app.dao.impl;

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
}