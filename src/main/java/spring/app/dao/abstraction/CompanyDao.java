package spring.app.dao.abstraction;

import spring.app.model.Company;

public interface CompanyDao extends GenericDao<Long, Company> {
    Company getCompanyByCompanyName(String companyName);

    /**
     * Получает компанию с заблокированными сущнстями Genre/Song/Author
     * @param id
     * @return Company
     */
    //Company getCompanyWithEntityBanned(Long id);
}
