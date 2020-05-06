package spring.app.dao.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.AddressDao;
import spring.app.model.Address;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class AddressDaoImpl extends AbstractDao<Long, Address> implements AddressDao {
    @Value("${accuracy}")
    private String accuracy;

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
        double INACCURACY = Double.valueOf(accuracy);

        double maxLongitude = address.getLongitude() + INACCURACY;
        double minLongitude = address.getLongitude() - INACCURACY;
        double maxLatitude = address.getLatitude() + INACCURACY;
        double minLatitude = address.getLatitude() - INACCURACY;

        List list = entityManager
                .createQuery("FROM Address WHERE latitude <= :maxLati AND latitude >= :minLati AND longitude <= :maxLong AND longitude >= :minLong", Address.class)
                .setParameter("maxLong", maxLongitude)
                .setParameter("minLong", minLongitude)
                .setParameter("maxLati", maxLatitude)
                .setParameter("minLati", minLatitude)
                .getResultList();

        return list;
    }

    @Override
    public Long getLastInsertId() {
        Long id;

        try {
            id = ((Number) entityManager.createNativeQuery("select max(id) from addresses;").getSingleResult()).longValue();
        } catch (NoResultException e) {
            return null;
        }

        return id;
    }
}
