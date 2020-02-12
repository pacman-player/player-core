package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.CompanyDao;
import spring.app.model.Bannable;
import spring.app.model.Company;
import spring.app.model.Song;
import spring.app.service.abstraction.CompanyService;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
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
    public Company getByCompanyName(String companyName) {
        return companyDao.getCompanyByCompanyName(companyName);
    }

    @Override
    public void removeById(Long id) {
        companyDao.deleteById(id);
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyDao.getAll();
    }

    @Override
    public boolean isExistCompanyByName(String name){
       return companyDao.isExistCompanyByName(name);
    }

    @Override
    public void checkAndMarkAllBlockedByTheCompany(Company company, List<? extends Bannable> bannables) {

        bannables.forEach(
                bannable -> bannable.setBanned(
                        bannable.isBannedBy(company)
                )
        );
    }

    @Override
    public Company setBannedEntity(Company company) {
        return companyDao.getCompanyWithEntityBanned(company.getId());
    }

    @Override
    public Company getCompanyByAddressId(long id) {
        return companyDao.getCompanyByAddressId(id);
    }

    @Override
    public List<String> getAllSongsInQueueByCompanyId(long id) {
        return companyDao.getAllSongsInQueueByCompanyId(id);
    }
}
