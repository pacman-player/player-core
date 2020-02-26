package spring.app.service.abstraction;

import spring.app.model.RegistrationStep;

import java.util.List;

public interface RegistrationStepService {

    RegistrationStep getRegStepByName(String name);

    RegistrationStep getRegStepById(Long id);

    void save(RegistrationStep registration);

    List<RegistrationStep> getAllRegSteps();

    void deleteRegStepById(Long id);

    void updateRegStep(RegistrationStep registrationStep);

    List<Long> getPassedRegStepsByUserId(Long userId);
    List<Long> getMissedRegStepsByUserId(Long userId);
}
