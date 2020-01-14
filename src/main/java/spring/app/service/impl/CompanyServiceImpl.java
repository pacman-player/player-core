package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.app.dao.abstraction.CompanyDao;
import spring.app.model.Bannable;
import spring.app.model.Company;
import spring.app.service.abstraction.CompanyService;

import java.util.Collections;
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
    public void checkAndMarkAllBlockedByTheCompany(Company company, List<? extends Bannable> bannables) {

        bannables.forEach(
                bannable -> bannable.setBanned(
                        bannable.isBannedBy(company)
                )
        );
    }

    /**
     * метод, который должен был проставлять компании заблокированные сущности: Author/Genre/Song
     * Но он не работает, так как в companyDao вылетает ошибка, когда в запросе с помощью JOIN FETCH
     * пытаешься получить поля заблокированных сущностей, в которых нет заблокированных сущностей.
     */
//    @Override
//    public void SetBannedEntity(Company company) {
//        Company supportComp = new Company();
//        supportComp.setBannedSong(Collections.emptySet());
//        supportComp.setBannedAuthor(Collections.emptySet());
//
//        supportComp = companyDao.getCompanyWithEntityBanned(company.getId());
//
//        company.setBannedAuthor(supportComp.getBannedAuthor());
//        company.setBannedSong(supportComp.getBannedSong());
//        company.setBannedGenres(supportComp.getBannedGenres());
//    }
}
