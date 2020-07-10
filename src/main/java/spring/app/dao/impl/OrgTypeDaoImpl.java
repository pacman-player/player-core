package spring.app.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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
        return orgType;
    }

    @Override
    public OrgType getDefault() {
        return entityManager.createQuery("SELECT o FROM OrgType o WHERE o.isDefault=true", OrgType.class)
                            .setMaxResults(1)
                            .getSingleResult();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void setDefaultById(long id) {
        entityManager.createQuery("UPDATE OrgType o SET o.isDefault = CASE WHEN o.id=:id THEN true ELSE false END")
                     .setParameter("id", id)
                     .executeUpdate();
    }
}
