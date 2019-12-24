package spring.app.service.abstraction;

import spring.app.model.OrgType;

import java.util.List;

public interface OrgTypeService {
    void addOrgType(OrgType orgType);

    List<OrgType> getAllOrgTypes();

    OrgType getOrgTypeById(long id);
}
