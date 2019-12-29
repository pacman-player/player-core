package spring.app.service.abstraction;

import spring.app.dto.SongRequest;
import spring.app.dto.SongResponse;

import java.io.IOException;

public interface TelegramService {
    SongResponse getSong(SongRequest songRequest) throws IOException;

}
