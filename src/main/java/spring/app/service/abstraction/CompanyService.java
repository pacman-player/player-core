package spring.app.service.abstraction;

import spring.app.model.Company;

import java.util.List;

public interface CompanyService {
    void addCompany(Company company);

    void updateCompany(Company company);

    Company getById(Long id);

    Company getByCompanyName(String companyName);

    void removeById(Long id);

    List<Company> getAllCompanies();

    boolean isExistCompanyByName(String name);
}
