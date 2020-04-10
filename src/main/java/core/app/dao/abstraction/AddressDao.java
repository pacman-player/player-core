package core.app.dao.abstraction;

import core.app.model.Address;

import java.util.List;

public interface AddressDao extends GenericDao<Long, Address> {
    Long getIdByLatitudeAndLongitude(String latitude, String longitude);
    List checkAddressInDB(Address address);
    Long getLastInsertId();
}
