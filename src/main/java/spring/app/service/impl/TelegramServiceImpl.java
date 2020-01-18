package spring.app.service.impl;

import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.DecoderException;
import org.springframework.stereotype.Service;
import spring.app.dto.SongRequest;
import spring.app.dto.SongResponse;
import spring.app.service.CutSongService;
import spring.app.service.abstraction.TelegramService;
import spring.app.service.abstraction.ZaycevSaitServise;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Service
public class TelegramServiceImpl implements TelegramService {

    private final ZaycevSaitServise zaycevSaitServise;
    private final CutSongService cutSongService;

    public TelegramServiceImpl(ZaycevSaitServise zaycevSaitServise, CutSongService cutSongService) {
        this.zaycevSaitServise = zaycevSaitServise;
        this.cutSongService = cutSongService;
    }

    @Override
    public SongResponse getSong(SongRequest songRequest) throws IOException {

        byte[] bytes = zaycevSaitServise.getSong(songRequest.getAuthorName(),songRequest.getSongName());
        SongResponse songResponse = new SongResponse(songRequest.getChatId(), 242345367l, bytes,
                songRequest.getAuthorName()+ " - " + songRequest.getSongName());
        return songResponse;
    }

    @Override
    public SongResponse approveSong(SongRequest songRequest) throws IOException, BitstreamException, DecoderException {
        byte[] bytes = zaycevSaitServise.getSong(songRequest.getAuthorName(),songRequest.getSongName());
        byte[] cutSong = cutSongService.сutSongMy(bytes, -1, 31);
        SongResponse songResponse = new SongResponse(songRequest.getChatId(), 242345367l, cutSong,
                songRequest.getAuthorName()+ " - " + songRequest.getSongName());
        return songResponse;
    }
}
