package core.app.service.impl;

import core.app.model.OrgType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import core.app.dao.abstraction.OrgTypeDao;
import core.app.service.abstraction.OrgTypeService;

import java.util.List;

@Service
@Transactional
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

    @Override
    public List<OrgType> getAllOrgTypes() {
        return orgTypeDao.getAll();
    }

    @Override
    public OrgType getOrgTypeById(long id) {
        return orgTypeDao.getById(id);
    }

    @Override
    public OrgType getByName(String name) {
        return orgTypeDao.getByName(name);
    }

    @Override
    public void deleteOrgTypeById(Long id) {
        orgTypeDao.deleteById(id);
    }

    @Override
    public void updateOrgType(OrgType orgType) {
        orgTypeDao.update(orgType);
    }
}
