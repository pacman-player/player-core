package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.app.dto.SearchSongDTO;
import spring.app.model.SongDownloadRequestInfo;
import spring.app.service.abstraction.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class MusicSearchAndStorageImpl implements MusicSearchAndStorage {

    private ZaycevSaitServise zaycevSaitServise;

    @Autowired
    public MusicSearchAndStorageImpl(ZaycevSaitServise zaycevSaitServise) {
        this.zaycevSaitServise = zaycevSaitServise;
    }

    public MusicSearchAndStorageImpl() {
    }
    //    @Autowired
//    private KrolikSaitService krolikSaitService;
//    @Autowired
//    private SongService songService;
//    @Autowired
//    private DownloadMusicVkRuService downloadMusicVkRuService;

//    @Autowired
//    private SongDownloadRequestInfoService songDownloadRequestInfoService;

    @Override
    public List<SearchSongDTO> searchBySongNameAndAuthorName(String songName, String authorName) {
//        ZaycevSaitServise zaycevSaitServise = new ZaycevSaitServiceImpl();
        KrolikSaitService krolikSaitService = new KrolikSaitServiceImpl();
        List<SongDownloadRequestInfo> songDownloadRequestInfoListink = new ArrayList<>();
        songDownloadRequestInfoListink = zaycevSaitServise.searchSongByAuthorOrSongs(authorName, songName);
//        for (SongDownloadRequestInfo songDownloadRequestInfo : songDownloadRequestInfoListink) {
//            songDownloadRequestInfoService.addSongDownloadRequestInfo(songDownloadRequestInfo);
//        }
        if (songDownloadRequestInfoListink.isEmpty()) {
            songDownloadRequestInfoListink = krolikSaitService.searchSongByAuthorOrSongs(authorName, songName);

        }


        System.out.println("111111111111111111");
        System.out.println(songDownloadRequestInfoListink.size());
        System.out.println(songDownloadRequestInfoListink);
        songDownloadRequestInfoListink.stream().forEach(songDownloadRequestInfo -> System.out.println(songDownloadRequestInfo.getAuthorName() +
                " " + songDownloadRequestInfo.getSongName() +
                " " + songDownloadRequestInfo.getLink()));
        return null;
    }

    @Override
    public List<SearchSongDTO> searchBySongName(String songName) {
        return null;
    }

    @Override
    public List<SearchSongDTO> searchByAuthorName(String authorName) {
        return null;
    }
}
