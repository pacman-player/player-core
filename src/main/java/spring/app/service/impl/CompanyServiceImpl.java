package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.app.dao.abstraction.CompanyDao;
import spring.app.model.Company;
import spring.app.service.abstraction.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyDao companyDao;

    @Autowired
    public CompanyServiceImpl(CompanyDao companyDao) {
        this.companyDao = companyDao;
    }

    @Override
    public void addCompany(Company company) {
        companyDao.save(company);
    }
}
