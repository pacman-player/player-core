package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.app.dto.SongRequest;
import spring.app.dto.SongResponce;
import spring.app.service.abstraction.TelegramService;
import spring.app.service.abstraction.ZaycevSaitServise;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class TelegramServiceImpl implements TelegramService {

    private final ZaycevSaitServise zaycevSaitServise;

    public TelegramServiceImpl(ZaycevSaitServise zaycevSaitServise) {
        this.zaycevSaitServise = zaycevSaitServise;
    }

    @Override
    public SongResponce getSong(SongRequest songRequest) throws IOException {

        byte[] bytes = zaycevSaitServise.getSong(songRequest.getAuthorName(),songRequest.getSongName());
        SongResponce songResponce = new SongResponce(songRequest.getChatId(), 242345367l, bytes,
                songRequest.getAuthorName()+ " - " + songRequest.getSongName());

        return songResponce;
    }
}
