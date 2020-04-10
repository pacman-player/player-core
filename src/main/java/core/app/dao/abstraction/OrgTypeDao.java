package core.app.dao.abstraction;

import core.app.model.OrgType;


public interface OrgTypeDao extends GenericDao<Long, OrgType> {
    OrgType getByName(String name);
}
