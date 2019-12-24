package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.app.dao.abstraction.CompanyDao;
import spring.app.model.Company;
import spring.app.service.abstraction.CompanyService;

import java.util.List;

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

    @Override
    public void updateCompany(Company company) {
        companyDao.update(company);
    }

    @Override
    public Company getById(Long id) {
        return companyDao.getById(id);
    }

    @Override
    public void removeById(Long id) {
        companyDao.deleteById(id);
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyDao.getAll();
    }
}
