package spring.app.dao.abstraction;

import spring.app.model.RegistrationStep;

import java.util.List;

public interface RegistrationStepDao extends GenericDao<Long, RegistrationStep> {
    RegistrationStep getRegStepByName(String stepName);
    List<Long> getPassedRegStepsByUserId(Long userId);
    List<Long> getMissedRegStepsByUserId(Long userId);
}
