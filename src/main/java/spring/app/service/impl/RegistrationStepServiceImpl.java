package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.RegistrationStepDao;
import spring.app.model.RegistrationStep;
import spring.app.service.abstraction.RegistrationStepService;

import java.util.List;

@Service
@Transactional
public class RegistrationStepServiceImpl extends AbstractServiceImpl<RegistrationStep, RegistrationStepDao> implements RegistrationStepService {

    @Autowired
    public RegistrationStepServiceImpl(RegistrationStepDao dao) {
        super(dao);
    }

    @Override
    public RegistrationStep getRegStepByName(String name) {
        return dao.getRegStepByName(name);
    }

    @Override
    public RegistrationStep getRegStepById(Long id) {
        return dao.getById(id);
    }

    @Override
    public void save(RegistrationStep registrationStep) {
        dao.save(registrationStep);
    }

    @Override
    public List<RegistrationStep> getAllRegSteps() {
        return dao.getAll();
    }

    @Override
    public void deleteRegStepById(Long id) {
        dao.deleteById(id);
    }

    @Override
    public void updateRegStep(RegistrationStep registrationStep) {
        dao.update(registrationStep);
    }

    @Override
    public List<Long> getPassedRegStepsByUserId(Long userId) {
        return dao.getPassedRegStepsByUserId(userId);
    }

    @Override
    public List<Long> getMissedRegStepsByUserId(Long userId) {
        return dao.getMissedRegStepsByUserId(userId);
    }
}
