package spring.app.dao.abstraction;

import spring.app.model.OrgType;


public interface OrgTypeDao extends GenericDao<Long, OrgType> {
    OrgType getByName(String name);
}
