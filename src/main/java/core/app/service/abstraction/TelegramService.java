package core.app.service.abstraction;

import core.app.dto.SongRequest;
import core.app.dto.SongResponse;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.DecoderException;

import java.io.IOException;

public interface TelegramService {
    SongResponse getSong(SongRequest songRequest) throws IOException, BitstreamException, DecoderException;

    SongResponse approveSong(SongRequest songRequest) throws IOException, BitstreamException, DecoderException;
}
