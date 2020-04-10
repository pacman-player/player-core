package core.app.service.abstraction;

import core.app.model.OrgType;

import java.util.List;


public interface OrgTypeService {

    void addOrgType(OrgType orgType);

    List<OrgType> getAllOrgTypes();

    OrgType getOrgTypeById(long id);

    OrgType getByName(String name);

    void deleteOrgTypeById(Long id);

    void updateOrgType(OrgType orgType);
}
