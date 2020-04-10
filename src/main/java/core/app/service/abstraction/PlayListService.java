package core.app.service.abstraction;

import core.app.model.PlayList;

public interface PlayListService {
    void addPlayList(PlayList playList);
    PlayList getPlayList(Long id);
    PlayList getPlayListByName(String name);
}
