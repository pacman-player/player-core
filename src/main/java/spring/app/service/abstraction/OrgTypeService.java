package spring.app.service.abstraction;

import spring.app.model.OrgType;


public interface OrgTypeService extends GenericService<Long, OrgType> {

    OrgType getByName(String name);

}
