package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.OrderSongDao;
import spring.app.dao.abstraction.OrgTypeDao;
import spring.app.model.OrderSong;
import spring.app.model.OrgType;
import spring.app.service.abstraction.OrgTypeService;

import java.util.List;

@Service
@Transactional
public class OrgTypeServiceImpl extends AbstractService<OrgType, OrgTypeDao> implements OrgTypeService {


    protected OrgTypeServiceImpl(OrgTypeDao dao) {
        super(dao);
    }

    @Override
    public void addOrgType(OrgType orgType) {
        dao.save(orgType);
    }

    @Override
    public List<OrgType> getAllOrgTypes() {
        return dao.getAll();
    }

    @Override
    public OrgType getOrgTypeById(long id) {
        return dao.getById(id);
    }

    @Override
    public OrgType getByName(String name) {
        return dao.getByName(name);
    }

    @Override
    public void deleteOrgTypeById(Long id) {
        dao.deleteById(id);
    }

    @Override
    public void updateOrgType(OrgType orgType) {
        dao.update(orgType);
    }
}
