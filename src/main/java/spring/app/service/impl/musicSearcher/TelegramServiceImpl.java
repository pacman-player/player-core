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

import java.io.IOException;

@Service
@Transactional
public class TelegramServiceImpl implements TelegramService {

    private final MusicSearchService musicSearchService;
    private final CutSongService cutSongService;

    public TelegramServiceImpl(MusicSearchService musicSearchService, CutSongService cutSongService) {
        this.musicSearchService = musicSearchService;
        this.cutSongService = cutSongService;
    }

    @Override
    public SongResponse getSong(SongRequest songRequest) throws IOException {

        byte[] track = musicSearchService.getSong(songRequest.getAuthorName(),songRequest.getSongName());
        SongResponse songResponse = new SongResponse(songRequest.getChatId(), 242345367l, track,
                musicSearchService.getTrackName());
        return songResponse;
    }

    @Override
    public SongResponse approveSong(SongRequest songRequest) throws IOException, BitstreamException, DecoderException {
        byte[] track = musicSearchService.getSong(songRequest.getAuthorName(),songRequest.getSongName());
        byte[] cutSong = cutSongService.сutSongMy(track, -1, 31);
        SongResponse songResponse = new SongResponse(songRequest.getChatId(), 242345367l, cutSong,
                musicSearchService.getTrackName());
        return songResponse;
    }
}
