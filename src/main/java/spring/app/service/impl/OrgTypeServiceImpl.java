package spring.app.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.OrgTypeDao;
import spring.app.dao.abstraction.dto.OrgTypeDtoDao;
import spring.app.dto.OrgTypeDto;
import spring.app.model.OrgType;
import spring.app.service.abstraction.OrgTypeService;

import java.util.List;

@Service
@Transactional
public class OrgTypeServiceImpl extends AbstractServiceImpl<Long, OrgType, OrgTypeDao> implements OrgTypeService {

    final private OrgTypeDtoDao orgTypeDtoDao;

    protected OrgTypeServiceImpl(OrgTypeDao dao, OrgTypeDtoDao orgTypeDtoDao) {
        super(dao);
        this.orgTypeDtoDao = orgTypeDtoDao;
    }

    @Override
    public OrgType getByName(String name) {
        return dao.getByName(name);
    }

    @Override
    public List<OrgTypeDto> FindAllOrgTypeDto() {
        return orgTypeDtoDao.getAllOrgType();
    }


}