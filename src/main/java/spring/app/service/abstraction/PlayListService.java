package spring.app.service.abstraction;

import java.util.List;


public interface PlayListService {

    void addPlayList(PlayList playList);
    List<PlayList> getAllPlayList();
}
