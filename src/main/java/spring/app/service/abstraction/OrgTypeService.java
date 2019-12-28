package spring.app.service.abstraction;

import spring.app.model.OrgType;

import java.util.List;

public interface OrgTypeService {
    void addOrgType(OrgType orgType);

    void deleteOrgTypeById(Long id);

    void updateOrgType(OrgType orgType);

    List<OrgType> getAllOrgType();
}
