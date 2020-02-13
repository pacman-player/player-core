package spring.app.dao.abstraction;

import spring.app.model.RegistrationStep;

public interface RegistrationStepDao extends GenericDao<Long, RegistrationStep> {
    RegistrationStep getRegStepByName(String stepName);
}
