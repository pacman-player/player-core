package core.app.dao.abstraction;

import core.app.model.Company;

import java.util.List;

public interface CompanyDao extends GenericDao<Long, Company> {
    Company getByIdWithAddress(Long id);

    Company getCompanyByCompanyName(String companyName);

    boolean isExistCompanyByName(String name);

    /**
     * Получает компанию с заблокированными сущнстями Genre/Song/Author
     *
     * @return Company
     */
    Company getCompanyWithEntityBanned(long id);

    Company getCompanyByAddressId(long id);

    List<String> getAllSongsInQueueByCompanyId(long id);
}
