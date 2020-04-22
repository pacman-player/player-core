package spring.app.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.OrgTypeDao;
import spring.app.model.OrgType;
import spring.app.service.abstraction.OrgTypeService;

@Service
@Transactional
public class OrgTypeServiceImpl extends AbstractServiceImpl<OrgType, OrgTypeDao, Long> implements OrgTypeService {


    protected OrgTypeServiceImpl(OrgTypeDao dao) {
        super(dao);
    }

    @Override
    public OrgType getByName(String name) {
        return dao.getByName(name);
    }
}