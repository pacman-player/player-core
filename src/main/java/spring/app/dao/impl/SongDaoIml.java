package spring.app.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.SongDao;
import spring.app.model.Song;

@Repository
@Transactional
public class SongDaoIml extends AbstractDao<Long, Song> implements SongDao {
    SongDaoIml() {
        super(Song.class);
    }
}
