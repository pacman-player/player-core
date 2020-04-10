package core.app.service.abstraction;

import core.app.service.impl.musicSearcher.serchServices.DownloadMusicVkRuServiceImpl;
import core.app.service.impl.musicSearcher.serchServices.KrolikSaitServiceImpl;
import core.app.service.impl.musicSearcher.serchServices.MuzofondfmMusicSearchImpl;
import core.app.service.impl.musicSearcher.serchServices.ZaycevSaitServiceImpl;
import core.app.service.entity.Track;

import java.io.IOException;

public interface DownloadMusicService {

    /**
     * Основной метод получения искомого трека.
     * @param author - имя исполнителя
     * @param song - название песни
     * @return возвращение нового экземпляра класса Track.
     *
     * @see MuzofondfmMusicSearchImpl
     * @see ZaycevSaitServiceImpl
     * @see DownloadMusicVkRuServiceImpl
     * @see KrolikSaitServiceImpl
     */
    Track getSong(String author, String song) throws IOException;

}
