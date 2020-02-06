package spring.app.service.impl.musicSearcher;

import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.DecoderException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dto.SongRequest;
import spring.app.dto.SongResponse;
import spring.app.service.CutSongService;
import spring.app.service.abstraction.MusicSearchService;
import spring.app.service.abstraction.TelegramService;
import spring.app.service.entity.Track;

import java.io.IOException;

@Service
@Transactional
public class TelegramServiceImpl implements TelegramService {
    private Track track = null;

    private final MusicSearchService musicSearchService;
    private final CutSongService cutSongService;

    public TelegramServiceImpl(MusicSearchService musicSearchService, CutSongService cutSongService) {
        this.musicSearchService = musicSearchService;
        this.cutSongService = cutSongService;
    }

    @Override
    public SongResponse getSong(SongRequest songRequest) throws IOException {
        if(track == null) {
            track = musicSearchService.getSong(songRequest.getAuthorName(), songRequest.getSongName());
        }
        String trackName = track.getFullTrackName();
        byte[] trackBytes = track.getTrack();
        SongResponse songResponse = new SongResponse(songRequest.getChatId(), 2344534L, trackBytes, trackName);
        return songResponse;
    }

    @Override
    public SongResponse approveSong(SongRequest songRequest) throws IOException, BitstreamException, DecoderException {
        track = musicSearchService.getSong(songRequest.getAuthorName(),songRequest.getSongName());
        String trackName = track.getFullTrackName();
        byte[] trackBytes = track.getTrack();
        byte[] cutSong = cutSongService.сutSongMy(trackBytes, -1, 31);
        SongResponse songResponse = new SongResponse(songRequest.getChatId(), 2344534L, cutSong, trackName);
        return songResponse;
    }
}
