package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.OrgTypeDao;
import spring.app.dao.abstraction.dto.OrgTypeDtoDao;
import spring.app.dto.OrgTypeDto;
import spring.app.model.OrgType;
import spring.app.service.abstraction.OrgTypeService;

import java.util.List;

@Service
public class OrgTypeServiceImpl extends AbstractServiceImpl<Long, OrgType, OrgTypeDao> implements OrgTypeService {

    private final OrgTypeDtoDao orgTypeDtoDao;

    @Autowired
    protected OrgTypeServiceImpl(OrgTypeDao dao, OrgTypeDtoDao orgTypeDtoDao) {
        super(dao);
        this.orgTypeDtoDao = orgTypeDtoDao;
    }

    @Override
    public OrgType getByName(String name) {
        return dao.getByName(name);
    }

    @Override
    public List<OrgTypeDto> getAllOrgTypeDto() {
        return orgTypeDtoDao.getAll();
    }
}