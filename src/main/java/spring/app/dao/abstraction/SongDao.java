package spring.app.dao.abstraction;

import spring.app.model.Song;

import java.util.List;

public interface SongDao extends GenericDao<Long, Song> {

    Song getByName(String name);
}

