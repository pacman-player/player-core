package spring.app.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.RegistrationStepDao;
import spring.app.model.RegistrationStep;

import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class RegistrationStepDaoImpl extends AbstractDao<Long, RegistrationStep> implements RegistrationStepDao {
    private final Logger LOGGER = LoggerFactory.getLogger(RegistrationStepDaoImpl.class);

    public RegistrationStepDaoImpl() {
        super(RegistrationStep.class);
    }

    @Override
    public RegistrationStep getById(Long id) {
        List<RegistrationStep> stepsList = entityManager.createQuery(
                "SELECT rg FROM RegistrationStep rg " +
                        "LEFT JOIN FETCH rg.usersOnStep WHERE rg.id = :id",
                RegistrationStep.class
        )
                .setParameter("id", id)
                .getResultList();
        if (stepsList.isEmpty()) {
            return null;
        }
        return stepsList.get(0);
    }

    @Override
    public RegistrationStep getRegStepByName(String stepName) {
        List<RegistrationStep> stepsList = entityManager.createQuery(
                "SELECT rg FROM RegistrationStep rg " +
                        "LEFT JOIN FETCH rg.usersOnStep WHERE rg.name = :name",
                RegistrationStep.class
        )
                .setParameter("name", stepName)
                .getResultList();
        if (stepsList.isEmpty()) {
            return null;
        }
        return stepsList.get(0);
    }

    @Override
    public List<Long> getPassedRegStepsByUserId(Long userId) {
        Query q = entityManager.createNativeQuery(
                "SELECT usr.registration_step_id " +
                        "FROM user_company usr " +
                        "WHERE usr.user_id = ?"
        );
        q.setParameter(1, userId);
        List stepsList = q.getResultList();

        List<Long> resultList = new ArrayList<>();
        for (Object num: stepsList
        ) {
            resultList.add(((BigInteger)num).longValue());
        }
        return resultList;
    }

    @Override
    public List<Long> getMissedRegStepsByUserId(Long userId) {
        Query q = entityManager.createNativeQuery(
                "SELECT rg.registration_step_id " +
                        "FROM registration_steps rg " +
                        "WHERE rg.registration_step_id " +
                        "NOT IN (" +
                        "SELECT usr.registration_step_id " +
                        "FROM user_on_registrationstep usr " +
                        "WHERE usr.user_id = ?" +
                        ")"
        );
        q.setParameter(1, userId);
        List stepsList = q.getResultList();

        List<Long> resultList = new ArrayList<>();
        for (Object num: stepsList
        ) {
            resultList.add(((BigInteger)num).longValue());
        }
        return resultList;
    }
}
