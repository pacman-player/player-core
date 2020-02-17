package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.UserCompanyDao;
import spring.app.model.RegistrationStep;
import spring.app.model.UserCompany;
import spring.app.service.abstraction.UserCompanyService;

import java.util.List;


@Service
@Transactional
public class UserCompanyServiceImpl implements UserCompanyService {

//    UserCompanyDao userCompanyDao;
//
//    @Autowired
//    public UserCompanyServiceImpl(UserCompanyDao userCompanyDao) {
//        this.userCompanyDao = userCompanyDao;
//    }
//
//    @Override
//    public UserCompany getUserCompanyById(UserCompany.UserCompanyKey key) {
//        return null;
//    }
//
//    @Override
//    public UserCompany getUserCompanyByUserId(Long userId) {
//        return null;
//    }
//
//    @Override
//    public UserCompany getUserCompanyByCompanyId(Long companyId) {
//        return null;
//    }
//
//    @Override
//    public void save(UserCompany userCompany) {
//        userCompanyDao.save(userCompany);
//    }
//
//    @Override
//    public void update(UserCompany userCompany) {
//        userCompanyDao.update(userCompany);
//    }
//
//    // ИСПРАВИТЬ ПО КАКОМУ КЛЮЧУ УДАЛЯЕТСЯ
//    @Override
//    public void delete(UserCompany userCompany) {
//        userCompanyDao.deleteById(userCompany.getUserCompanyId().getUserId());
//    }
//
//    @Override
//    public List<UserCompany> getAllUserCompanies() {
//        return userCompanyDao.getAll();
//    }
//
//    @Override
//    public List<RegistrationStep> getAllRegSteps(){
//        return userCompanyDao.getAllRegSteps();
//    }
//
//    @Override
//    public List<Long> getPassedSteps(Long userId){
//        return userCompanyDao.getPassedRegSteps(userId);
//    }
//
//    @Override
//    public List<Long> getMissedRegSteps(Long userId) {
//        return userCompanyDao.getMissedRegSteps(userId);
//    }
}
