package spring.app.service.abstraction;

import spring.app.model.RegistrationStep;

import java.util.List;

public interface RegistrationStepService extends GenericService<Long, RegistrationStep> {

    RegistrationStep getByName(String name);

    List<Long> getPassedRegStepsByUserId(Long userId);

    List<Long> getMissedRegStepsByUserId(Long userId);
}
