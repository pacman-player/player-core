package spring.app.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.CompanyDao;
import spring.app.model.Company;

@Repository
@Transactional
public class CompanyDaoImpl  extends AbstractDao<Long, Company> implements CompanyDao {
    CompanyDaoImpl() {
        super(Company.class);
    }
}
