package spring.app.dao.abstraction;

import spring.app.model.Company;

import java.util.List;

public interface CompanyDao extends GenericDao<Long, Company> {
    Company getCompanyByCompanyName(String companyName);
    boolean isExistCompanyByName(String name);

    /**
     * Получает компанию с заблокированными сущнстями Genre/Song/Author
     * @return Company
     */
    Company getCompanyWithEntityBanned(long id);
}
