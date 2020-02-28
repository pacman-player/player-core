package spring.app.service.abstraction;

import spring.app.model.Bannable;
import spring.app.model.Company;
import spring.app.model.Genre;
import spring.app.model.Song;

import java.util.List;

public interface CompanyService {
    void addCompany(Company company);

    void updateCompany(Company company);

    Company getById(Long id);

    Company getByIdWithAddress(Long id);

    Company getByCompanyName(String companyName);

    void removeById(Long id);

    List<Company> getAllCompanies();

    boolean isExistCompanyByName(String name);

    /**
     * Проверяет какие обьекты у компании добавлены в бан
     * (Author или Music или Song)
     * если обект заблокирован, то в переданной коллекции у данного объекта меняет
     * поле banned на true.
     */
    void checkAndMarkAllBlockedByTheCompany(Company company, List<? extends Bannable> bannable);

    /**
     * заполняет поля с заблокированными сущностями песен/жанров/авторов у компании
     */
    Company setBannedEntity(Company company);

    Company getCompanyByAddressId(long id);

    List<String> getAllSongsInQueueByCompanyId(long id);
}
