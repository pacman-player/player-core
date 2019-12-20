package spring.app.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.SongСompilationDao;
import spring.app.model.SongСompilation;
import spring.app.model.User;


@Repository
@Transactional
public class SongСompilationDaoImpl extends AbstractDao<Long, SongСompilation> implements SongСompilationDao {

    SongСompilationDaoImpl() {
        super(SongСompilation.class);
    }
}
