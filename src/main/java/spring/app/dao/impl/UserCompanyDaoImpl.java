package spring.app.dao.impl;

import org.hibernate.type.LongType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.GenericDao;
import spring.app.dao.abstraction.UserCompanyDao;
import spring.app.model.RegistrationStep;
import spring.app.model.User;
import spring.app.model.UserCompany;

import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


@Repository
@Transactional(readOnly = true)
public class UserCompanyDaoImpl extends AbstractDao<Long, UserCompany> implements UserCompanyDao {
    private final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);

    public UserCompanyDaoImpl() {
        super(UserCompany.class);
    }

    @Override
    public List<Long> getMissedRegSteps(Long userId) {
        Query q = entityManager.createNativeQuery(
                "SELECT rg.id " +
                        "FROM registration_step rg " +
                        "WHERE rg.id " +
                        "NOT IN (" +
                        "SELECT usr.reg_step_id " +
                        "FROM user_company usr " +
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

    @Override
    public List<Long> getPassedRegSteps(Long userId) {
        Query q = entityManager.createNativeQuery(
                "SELECT usr.reg_step_id " +
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
    public List<RegistrationStep> getAllRegSteps() {
        List<RegistrationStep> registrationStepList = entityManager.createQuery(
                "SELECT rg FROM RegistrationStep rg ",
                RegistrationStep.class
        )
                .getResultList();
        return registrationStepList;
    }
}
