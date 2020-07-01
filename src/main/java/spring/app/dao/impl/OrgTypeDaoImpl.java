package spring.app.dao.impl;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;
import spring.app.dao.abstraction.OrgTypeDao;
import spring.app.model.OrgType;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;


@Repository
public class OrgTypeDaoImpl extends AbstractDao<Long, OrgType> implements OrgTypeDao {

    OrgTypeDaoImpl() {
        super(OrgType.class);
    }

    @Override
    public OrgType getByName(String name) {
        TypedQuery<OrgType> query = entityManager.createQuery("FROM OrgType WHERE name = :name", OrgType.class);
        query.setParameter("name", name);
        OrgType orgType;
        try {
            orgType = query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        Hibernate.initialize(orgType.getGenres());
        return orgType;
    }
}
