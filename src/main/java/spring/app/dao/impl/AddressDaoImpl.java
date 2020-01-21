package spring.app.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.AddressDao;
import spring.app.model.Address;

import javax.persistence.NoResultException;
import java.text.DecimalFormat;
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
    public List checkAddressInDB(Address address) {
        String strLong = String.format("%.3f", address.getLongitude()).replace(',', '.');
        String strLati = String.format("%.3f", address.getLatitude()).replace(',', '.');
        double longitude = new Double(strLong);
        double latitude = new Double(strLati);
        List list = entityManager
                .createQuery("FROM Address WHERE latitude > :latitude AND longitude > :longitude", Address.class)
                .setParameter("latitude", latitude)
                .setParameter("longitude", longitude)
                .getResultList();

        return list;
    }
}
