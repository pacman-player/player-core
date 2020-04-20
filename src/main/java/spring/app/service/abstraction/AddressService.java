package spring.app.service.abstraction;

import spring.app.model.Address;

import java.util.List;

public interface AddressService extends GenericService<Address>{
//public interface AddressService {

    Long getId(String latitude, String longitude);

    List checkAddress(Address address);

    Long getLastId();
}
