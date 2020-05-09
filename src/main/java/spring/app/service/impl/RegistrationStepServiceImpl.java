package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.RegistrationStepDao;
import spring.app.model.RegistrationStep;
import spring.app.service.abstraction.RegistrationStepService;

import java.util.List;

@Service
public class RegistrationStepServiceImpl extends AbstractServiceImpl<Long, RegistrationStep, RegistrationStepDao> implements RegistrationStepService {

    @Autowired
    public RegistrationStepServiceImpl(RegistrationStepDao dao) {
        super(dao);
    }

    @Override
    public RegistrationStep getByName(String name) {
        return dao.getRegStepByName(name);
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
