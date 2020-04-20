package spring.app.service.abstraction;

import spring.app.model.OrderSong;
import spring.app.model.OrgType;

import java.util.List;


//public interface OrgTypeService  extends GenericService<OrgType>{
public interface OrgTypeService{

    void addOrgType(OrgType orgType);

    List<OrgType> getAllOrgTypes();

    OrgType getOrgTypeById(long id);

    OrgType getByName(String name);

    void deleteOrgTypeById(Long id);

    void updateOrgType(OrgType orgType);
}
