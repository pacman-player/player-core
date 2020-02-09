package spring.app.service.impl.musicSearcher;

import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.DecoderException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dto.SongRequest;
import spring.app.dto.SongResponse;
import spring.app.service.CutSongService;
import spring.app.service.abstraction.FindMusicInDBService;
import spring.app.service.abstraction.MusicSearchService;
import spring.app.service.abstraction.SongService;
import spring.app.service.abstraction.TelegramService;
import spring.app.service.entity.Track;

import java.io.IOException;

@Service
@Transactional
public class TelegramServiceImpl implements TelegramService {

    private Track track = null;
    private String trackName;
    private Long songId;

    private final SongService songService;
    private final MusicSearchService musicSearchService;
    private final CutSongService cutSongService;
    private final FindMusicInDBService fromDBMusicService;

    public TelegramServiceImpl(MusicSearchService musicSearchService, CutSongService cutSongService,
                               FindMusicInDBService fromDBMusicService, SongService songService) {
        this.musicSearchService = musicSearchService;
        this.cutSongService = cutSongService;
        this.fromDBMusicService = fromDBMusicService;
        this.songService = songService;
    }

    @Override
    public SongResponse getSong(SongRequest songRequest) throws IOException {
        if(track == null) {
            track = musicSearchService.getSong(songRequest.getAuthorName(), songRequest.getSongName());
            songId = musicSearchService.updateData(track);
        }
        String trackName = track.getFullTrackName();
        byte[] trackBytes = track.getTrack();

        SongResponse songResponse = new SongResponse(songRequest.getChatId(), songId, trackBytes, trackName);
        return songResponse;
    }

    @Override
    public SongResponse approveSong(SongRequest songRequest) throws IOException, BitstreamException, DecoderException {
        trackName = musicSearchService.findSongInfo(songRequest.getAuthorName(), songRequest.getSongName());
        track = fromDBMusicService.findTrackFromDB(trackName); // ищи песню в БД?
        if(track == null) {
            track = musicSearchService.getSong(songRequest.getAuthorName(), songRequest.getSongName()); // ищи песню в интернете
            songId = musicSearchService.updateData(track);  //сохраняй данные песни в БД
        } else songId = songService.getByName(track.getSong()).getId();
        byte[] trackBytes = track.getTrack();
        byte[] cutSong = cutSongService.сutSongMy(trackBytes, -1, 31);
        SongResponse songResponse = new SongResponse(songRequest.getChatId(), songId, cutSong, trackName);
        return songResponse;
    }
}
