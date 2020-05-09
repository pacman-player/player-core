package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.CompanyDao;
import spring.app.dao.abstraction.OrderSongDao;
import spring.app.dao.abstraction.dto.CompanyDtoDao;
import spring.app.dto.CompanyDto;
import spring.app.model.Bannable;
import spring.app.model.Company;
import spring.app.service.abstraction.CompanyService;

import java.util.List;

@Service
public class CompanyServiceImpl extends AbstractServiceImpl<Long, Company, CompanyDao> implements CompanyService {


    private final OrderSongDao orderSongDao;
    private final CompanyDtoDao companyDtoDao;


    @Autowired
    public CompanyServiceImpl(CompanyDao dao, CompanyDtoDao companyDtoDao, OrderSongDao orderSongDao) {
        super(dao);
        this.companyDtoDao = companyDtoDao;
        this.orderSongDao = orderSongDao;
    }


    @Override
    public CompanyDto getCompanyDtoById(Long id) {
        return companyDtoDao.getById(id);
    }

    @Override
    public Company getByIdWithAddress(Long id) {
        return dao.getByIdWithAddress(id);
    }

    @Override
    public Company getByCompanyName(String companyName) {
        return dao.getCompanyByCompanyName(companyName);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        orderSongDao.bulkRemoveOrderSongByCompany(id);
        dao.deleteById(id);

    }

    @Override
    public List<CompanyDto> getAllCompanies() {
        return companyDtoDao.getAllCompanies();

    }

    @Override
    public boolean isExistCompanyByName(String name) {
        return dao.isExistCompanyByName(name);
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
        return dao.getCompanyWithEntityBanned(company.getId());
    }

    @Override
    public Company getCompanyByAddressId(Long id) {
        return dao.getCompanyByAddressId(id);
    }

    @Override
    public List<String> getAllSongsInQueueByCompanyId(long id) {
        return dao.getAllSongsInQueueByCompanyId(id);
    }
}
