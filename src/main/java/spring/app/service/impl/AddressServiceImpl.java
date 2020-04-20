package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.AddressDao;
import spring.app.dao.abstraction.UserDao;
import spring.app.model.Address;
import spring.app.model.User;
import spring.app.service.abstraction.AddressService;

import java.util.List;

@Service
@Transactional
public class AddressServiceImpl extends AbstractService<Address, AddressDao> implements AddressService {

    @Autowired
    public AddressServiceImpl(AddressDao addressDao) {
        super(addressDao);
    }

    @Override
    public void addAddress(Address address) {
        dao.save(address);
    }

    @Override
    public void updateAddress(Address address) {
        dao.update(address);
    }

    @Override
    public Address getById(Long id) {
        return dao.getById(id);
    }

    @Override
    public void removeById(Long id) {
        dao.deleteById(id);
    }

    @Override
    public List<Address> getAllAddresses() {
        return dao.getAll();
    }

    @Override
    public Long getId(String latitude, String longitude) {
        return dao.getIdByLatitudeAndLongitude(latitude, longitude);
    }

    @Override
    public List checkAddress(Address address) {
        return dao.checkAddressInDB(address);
    }

    @Override
    public Long getLastId() {
        return dao.getLastInsertId();
    }
}
