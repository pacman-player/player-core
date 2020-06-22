package spring.app.service.abstraction;

import spring.app.model.Address;

import java.util.List;

public interface AddressService extends GenericService<Long, Address>{

    Long getId(String latitude, String longitude);

    List checkAddress(Address address);

    Long getLastId();
}
