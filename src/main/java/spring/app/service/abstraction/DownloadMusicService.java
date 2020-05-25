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
     * @see spring.app.service.impl.musicSearcher.searchServices.MuzofondfmMusicSearchImpl
     * @see spring.app.service.impl.musicSearcher.searchServices.ZaycevSaitServiceImpl
     * @see spring.app.service.impl.musicSearcher.searchServices.DownloadMusicVkRuServiceImpl
     * @see spring.app.service.impl.musicSearcher.searchServices.KrolikSaitServiceImpl
     */
    Track getSong(String author, String song) throws IOException;

}
