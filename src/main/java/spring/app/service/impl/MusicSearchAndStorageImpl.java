package spring.app.service.impl;

import org.springframework.stereotype.Service;
import spring.app.dto.SearchSongDTO;
import spring.app.model.Song;
import spring.app.model.SongDownloadRequestInfo;
import spring.app.service.abstraction.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class MusicSearchAndStorageImpl implements MusicSearchAndStorage {
    private final ZaycevSaitServise zaycevSaitServise;
    private final KrolikSaitService krolikSaitService;
    private final SongDownloadRequestInfoService songDownloadRequestInfoService;
    private final SongService songService;
    private final AuthorService authorService;


    public MusicSearchAndStorageImpl(ZaycevSaitServise zaycevSaitServise, KrolikSaitService krolikSaitService, SongDownloadRequestInfoService songDownloadRequestInfoService, SongService songService, AuthorService authorService) {
        this.zaycevSaitServise = zaycevSaitServise;
        this.krolikSaitService = krolikSaitService;
        this.songDownloadRequestInfoService = songDownloadRequestInfoService;
        this.songService = songService;
        this.authorService = authorService;
    }
//    @Autowired
//    private DownloadMusicVkRuService downloadMusicVkRuService;


    @Override
    public List<SearchSongDTO> searchBySongNameAndAuthorName(String songName, String authorName) {
        List<SearchSongDTO> songDTOList = new ArrayList<>();
        List<SongDownloadRequestInfo> songDownloadRequestInfoListink = new ArrayList<>();


        songDownloadRequestInfoListink = zaycevSaitServise.searchSongByAuthorOrSongs(authorName, songName);

        if (songDownloadRequestInfoListink.isEmpty()) {
            songDownloadRequestInfoListink = krolikSaitService.searchSongByAuthorOrSongs(authorName, songName);
        }

        for (SongDownloadRequestInfo songDownloadRequestInfo : songDownloadRequestInfoListink) {
            songDownloadRequestInfoService.addSongDownloadRequestInfo(songDownloadRequestInfo);
        }

        List<SongDownloadRequestInfo> all = songDownloadRequestInfoService.getAll();

        all.stream().limit(10).forEach(songDownloadRequestInfo -> songDTOList.add(new SearchSongDTO(songDownloadRequestInfo.getId(), songDownloadRequestInfo.getAuthorName(), songDownloadRequestInfo.getSongName())));

        System.out.println("Сохранили в БД");
        System.out.println(all.size());
        System.out.println(songDownloadRequestInfoListink.size());
        all.stream().forEach(songDownloadRequestInfo -> System.out.println(songDownloadRequestInfo.getAuthorName() +
                " " + songDownloadRequestInfo.getSongName() +
                " " + songDownloadRequestInfo.getLink()));

        return songDTOList;
    }

    @Override
    public List<SearchSongDTO> searchBySongName(String songName) {
        return null;
    }

    @Override
    public List<SearchSongDTO> searchByAuthorName(String authorName) {
        return null;
    }

    private SearchSongDTO searchInDb(String authorName, String songName) {
        //check
        Long id = authorService.getByName(authorName).getId();
        if (id > 0) {
            //check
            //[]
            Song bySongNameAndAuthorId = songService.getBySongNameAndAuthorId(id, songName);
            if (bySongNameAndAuthorId.getId() > 0) {
                return new SearchSongDTO(bySongNameAndAuthorId.getId(), bySongNameAndAuthorId.getAuthor().getName(), bySongNameAndAuthorId.getName());
            }
        }
        return null;
    }
}
