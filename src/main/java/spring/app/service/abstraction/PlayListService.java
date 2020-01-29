package spring.app.service.abstraction;

import spring.app.model.PlayList;

public interface PlayListService {
    void addPlayList(PlayList playList);
    PlayList getPlayList(Long id);
    PlayList getPlayListByName(String name);
}
