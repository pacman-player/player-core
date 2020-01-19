package spring.app.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.AddressDao;
import spring.app.model.Address;

import javax.persistence.NoResultException;
import java.sql.ResultSet;
import java.util.List;

@Repository
@Transactional
public class AddressDaoImpl extends AbstractDao<Long, Address> implements AddressDao {
    public AddressDaoImpl() {
        super(Address.class);
    }

    @Override
    public Long getIdByLatitudeAndLongitude(String latitude, String longitude) {
        Long id;

        try {
            id = entityManager
                    .createQuery("SELECT Address.id FROM Address A WHERE A.latitude = :latitude", Address.class)
                    .setParameter("latitude", latitude)
//                    .setParameter("longitude", longitude)
                    .getResultList()
                    .get(0)
                    .getId();
        } catch (NoResultException e) {
            return null;
        }

        return id;
    }

    @Override
    public boolean checkAddressInDB(Address address) {
        List list = entityManager
                .createQuery("SELECT * FROM Address A WHERE A.latitude = :latitude AND A.longitude = :longitude AND A.country = :country AND A.city = :city AND A.street = :street", Address.class)
                .setParameter("latitude", address.getLatitude())
                .setParameter("longitude", address.getLongitude())
                .setParameter("country", address.getCountry())
                .setParameter("city", address.getCity())
                .setParameter("street", address.getStreet())
                .getResultList();
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }
}
