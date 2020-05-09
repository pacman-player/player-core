package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.AddressDao;
import spring.app.model.Address;
import spring.app.service.abstraction.AddressService;

import java.util.List;

@Service
public class AddressServiceImpl extends AbstractServiceImpl<Long, Address, AddressDao> implements AddressService {

    @Autowired
    public AddressServiceImpl(AddressDao addressDao) {
        super(addressDao);
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
