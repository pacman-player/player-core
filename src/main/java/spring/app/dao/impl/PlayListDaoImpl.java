package spring.app.dao.impl;

import org.springframework.stereotype.Repository;
import spring.app.dao.abstraction.PlayListDao;

import java.util.List;

@Repository
public class PlayListDaoImpl extends AbstractDao<Long, PlayList> implements PlayListDao {

    PlayListDaoImpl() {
        super(PlayList.class);
    }

    @Override
    public List<PlayList> getAll() {
        return super.getAll();
    }
}
