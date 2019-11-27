package spring.app.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.PlayListDao;
import spring.app.model.PlayList;

@Repository
@Transactional
public class PlayListDaoImpl extends AbstractDao<Long, PlayList> implements PlayListDao {
    PlayListDaoImpl() {
        super(PlayList.class);
    }
}
