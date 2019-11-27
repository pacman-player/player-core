package spring.app.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.SongQueueDao;
import spring.app.model.SongQueue;

@Repository
@Transactional
public class SongQueueDaoImpl extends AbstractDao<Long, SongQueue> implements SongQueueDao {
    SongQueueDaoImpl() {
        super(SongQueue.class);
    }
}
