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
public class RegistrationStepServiceImpl implements RegistrationStepService {
    RegistrationStepDao registrationStepDao;

    @Autowired
    public RegistrationStepServiceImpl(RegistrationStepDao registrationStepDao) {
        this.registrationStepDao = registrationStepDao;
    }

    @Override
    public RegistrationStep getRegStepByName(String name) {
        return registrationStepDao.getRegStepByName(name);
    }

    @Override
    public RegistrationStep getRegStepById(Long id) {
        return registrationStepDao.getById(id);
    }

    @Override
    public void save(RegistrationStep registrationStep) {
        registrationStepDao.save(registrationStep);
    }

    @Override
    public List<RegistrationStep> getAllRegSteps() {
        return registrationStepDao.getAll();
    }

    @Override
    public void deleteRegStepById(Long id) {
        registrationStepDao.deleteById(id);
    }

    @Override
    public void updateRegStep(RegistrationStep registrationStep) {
        registrationStepDao.update(registrationStep);
    }

    @Override
    public List<Long> getPassedRegStepsByUserId(Long userId) {
        return registrationStepDao.getPassedRegStepsByUserId(userId);
    }

    @Override
    public List<Long> getMissedRegStepsByUserId(Long userId) {
        return registrationStepDao.getMissedRegStepsByUserId(userId);
    }
}
