package spring.app.service.abstraction;

import spring.app.model.Bannable;
import spring.app.model.Company;
import spring.app.model.Genre;

import java.util.List;

public interface CompanyService {
    void addCompany(Company company);

    void updateCompany(Company company);

    Company getById(Long id);

    void removeById(Long id);

    List<Company> getAllCompanies();

    /**
     * Проверяет какие обьекты у компании добавлены в бан
     * (Author или Music или Song)
     * если обект заблокирован, то в переданной коллекции у данного объекта меняет
     * поле banned на true.
     */
    void checkAndMarkAllBlockedByTheCompany(Company company, List<? extends Bannable> bannable);
}
