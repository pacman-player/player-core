package spring.app.service.abstraction;

import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.DecoderException;
import spring.app.dto.SongRequest;
import spring.app.dto.SongResponse;

import java.io.IOException;

public interface TelegramService {
    SongResponse getSong(SongRequest songRequest) throws IOException, BitstreamException, DecoderException;

    SongResponse approveSong(SongRequest songRequest) throws IOException, BitstreamException, DecoderException;
}
