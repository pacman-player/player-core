package spring.app.dao.abstraction;

import spring.app.model.Address;

public interface AddressDao extends GenericDao<Long, Address> {
    Long getIdByLatitudeAndLongitude(String latitude, String longitude);
}
