package spring.app.service.abstraction;

import spring.app.dto.OrgTypeDto;
import spring.app.model.OrgType;

import java.util.List;


public interface OrgTypeService extends GenericService<Long, OrgType> {

    OrgType getByName(String name);

    List<OrgTypeDto> getAllOrgTypeDto();
}
