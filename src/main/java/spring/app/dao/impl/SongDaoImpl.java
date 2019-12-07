package spring.app.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.SongDao;
import spring.app.model.Company;
import spring.app.model.Song;

import javax.persistence.TypedQuery;

@Repository
@Transactional
public class SongDaoImpl extends AbstractDao<Long, Song> implements SongDao {
    SongDaoImpl() {
        super(Song.class);
    }
}
