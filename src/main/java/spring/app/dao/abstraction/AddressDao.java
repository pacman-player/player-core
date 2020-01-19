package spring.app.dao.abstraction;

import com.vk.api.sdk.objects.ads.Ad;
import spring.app.model.Address;

public interface AddressDao extends GenericDao<Long, Address> {
    Long getIdByLatitudeAndLongitude(String latitude, String longitude);
    boolean checkAddressInDB(Address address);
}
