package spring.app.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.VisitDao;
import spring.app.model.Visit;
//import spring.app.model.VisitPK;

import java.sql.Timestamp;

@Repository
@Transactional(readOnly = true)
public class VisitDaoImpl
        extends AbstractDao<Long, Visit>
        implements VisitDao {

    VisitDaoImpl() {
        super(Visit.class);
    }

}
