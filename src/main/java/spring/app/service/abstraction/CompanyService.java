package spring.app.service.abstraction;

import spring.app.dto.CompanyDto;
import spring.app.model.Bannable;
import spring.app.model.Company;

import java.util.List;

public interface CompanyService extends GenericService<Long, Company> {

    CompanyDto getCompanyDtoById(Long id);

    Company getByIdWithAddress(Long id);

    Company getByCompanyName(String companyName);

    List<CompanyDto> getAllCompanies();

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

    Company getCompanyByAddressId(Long id);

    List<String> getAllSongsInQueueByCompanyId(long id);
}
