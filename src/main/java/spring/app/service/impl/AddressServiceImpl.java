package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.AddressDao;
import spring.app.model.Address;
import spring.app.service.abstraction.AddressService;

import java.util.List;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {
    private final AddressDao addressDao;

    @Autowired
    public AddressServiceImpl(AddressDao addressDao) {
        this.addressDao = addressDao;
    }

    @Override
    public void addAddress(Address address) {
        addressDao.save(address);
    }

    @Override
    public void updateAddress(Address address) {
        addressDao.update(address);
    }

    @Override
    public Address getById(Long id) {
        return addressDao.getById(id);
    }

    @Override
    public void removeById(Long id) {
        addressDao.deleteById(id);
    }

    @Override
    public List<Address> getAllAddresses() {
        return addressDao.getAll();
    }

    @Override
    public Long getId(String latitude, String longitude) {
        return addressDao.getIdByLatitudeAndLongitude(latitude, longitude);
    }

    @Override
    public List checkAddress(Address address) {
        return addressDao.checkAddressInDB(address);
    }

    @Override
    public Long getLastId() {
        return addressDao.getLastInsertId();
    }
}
