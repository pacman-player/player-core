package spring.app.service.abstraction;

import spring.app.service.entity.Track;

import java.io.IOException;

public interface DownloadMusicService {

    /**
     * Основной метод получения искомого трека.
     *
     * @param author - имя исполнителя
     * @param song   - название песни
     * @return возвращение нового экземпляра класса Track.
     *
     * @see spring.app.service.impl.musicSearcher.serchServices.MuzofondfmMusicSearchImpl
     * @see spring.app.service.impl.musicSearcher.serchServices.ZaycevSaitServiceImpl
     * @see spring.app.service.impl.musicSearcher.serchServices.DownloadMusicVkRuServiceImpl
     * @see spring.app.service.impl.musicSearcher.serchServices.KrolikSaitServiceImpl
     * @see spring.app.service.impl.musicSearcher.serchServices.BDSearchServiceImpl
     */
    Track getSong(String author, String song) throws IOException;

}
