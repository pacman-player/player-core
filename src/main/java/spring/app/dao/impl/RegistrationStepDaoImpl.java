package spring.app.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.RegistrationStepDao;
import spring.app.model.RegistrationStep;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class RegistrationStepDaoImpl extends AbstractDao<Long, RegistrationStep> implements RegistrationStepDao {
    private final Logger LOGGER = LoggerFactory.getLogger(RegistrationStepDaoImpl.class);

    public RegistrationStepDaoImpl() {
        super(RegistrationStep.class);
    }

    @Override
    public RegistrationStep getRegStepByName(String stepName) {
        List<RegistrationStep> stepsList = entityManager.createQuery(
                "FROM RegistrationStep WHERE name = :name",
                RegistrationStep.class
        )
                .setParameter("name", stepName)
                .getResultList();
        if (stepsList.isEmpty()) {
            return null;
        }
        return stepsList.get(0);
    }
}
