package spring.app.service.abstraction;

import spring.app.model.Company;

public interface CompanyService {
    void addCompany(Company company);

    void updateCompany(Company company);

    Company getById(Long id);

    Company getByCompanyName(String companyName);

    void removeById(Long id);
}
