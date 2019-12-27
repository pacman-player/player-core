package spring.app.service.impl;

import org.springframework.stereotype.Service;
import spring.app.dto.SongRequest;
import spring.app.dto.SongResponse;
import spring.app.service.abstraction.TelegramService;
import spring.app.service.abstraction.ZaycevSaitServise;

import java.io.IOException;

@Service
public class TelegramServiceImpl implements TelegramService {

    private final ZaycevSaitServise zaycevSaitServise;

    public TelegramServiceImpl(ZaycevSaitServise zaycevSaitServise) {
        this.zaycevSaitServise = zaycevSaitServise;
    }

    @Override
    public SongResponse getSong(SongRequest songRequest) throws IOException {

        byte[] bytes = zaycevSaitServise.getSong(songRequest.getAuthorName(),songRequest.getSongName());
        SongResponse songResponse = new SongResponse(songRequest.getChatId(), 242345367l, bytes,
                songRequest.getAuthorName()+ " - " + songRequest.getSongName());

        return songResponse;
    }
}
