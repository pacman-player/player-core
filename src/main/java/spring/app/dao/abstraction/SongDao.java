package spring.app.dao.abstraction;

import spring.app.model.Song;

public interface SongDao extends GenericDao<Long, Song> {

    Song getByName(String name);

    Song getByNameAndAuthor(String name, String author);

    boolean isExist(String name);
}

