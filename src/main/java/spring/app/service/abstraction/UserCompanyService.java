package spring.app.service.abstraction;

import spring.app.model.RegistrationStep;
import spring.app.model.UserCompany;

import java.util.List;

public interface UserCompanyService {
    UserCompany getUserCompanyById(UserCompany.UserCompanyKey key);

    UserCompany getUserCompanyByUserId(Long userId);

    UserCompany getUserCompanyByCompanyId(Long companyId);

    void save(UserCompany userCompany);

    void update(UserCompany userCompany);

    void delete(UserCompany userCompany);

    List<UserCompany> getAllUserCompanies();

    List<RegistrationStep> getAllRegSteps();

    List<Long> getPassedSteps(Long userId);

    List<Long> getMissedRegSteps(Long userId);
}
