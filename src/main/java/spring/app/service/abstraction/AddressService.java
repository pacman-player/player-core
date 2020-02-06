package spring.app.service.abstraction;

import spring.app.model.Address;

import java.util.List;

public interface AddressService {
    void addAddress(Address address);

    void updateAddress(Address address);

    Address getById(Long id);

    void removeById(Long id);

    List<Address> getAllAddresses();

    Long getId(String latitude, String longitude);

    List checkAddress(Address address);

    Long getLastId();
}
