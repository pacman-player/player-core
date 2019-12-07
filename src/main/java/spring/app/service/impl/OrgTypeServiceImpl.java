package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.app.dao.abstraction.OrgTypeDao;
import spring.app.model.OrgType;
import spring.app.service.abstraction.OrgTypeService;

@Service
public class OrgTypeServiceImpl implements OrgTypeService {

    private final OrgTypeDao orgTypeDao;

    @Autowired
    public OrgTypeServiceImpl(OrgTypeDao orgTypeDao) {
        this.orgTypeDao = orgTypeDao;
    }

    @Override
    public void addOrgType(OrgType orgType) {
        orgTypeDao.save(orgType);
    }
}
