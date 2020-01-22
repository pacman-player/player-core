package spring.app.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.OrgTypeDao;
import spring.app.model.OrgType;

@Repository
@Transactional(readOnly = true)
public class OrgTypeDaoImpl extends AbstractDao<Long, OrgType> implements OrgTypeDao {
    OrgTypeDaoImpl() {
        super(OrgType.class);
    }
}
