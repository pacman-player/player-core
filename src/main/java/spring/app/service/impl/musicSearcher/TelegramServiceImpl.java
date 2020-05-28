package spring.app.service.impl.musicSearcher;

import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.DecoderException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dto.BotSongDto;
import spring.app.dto.SongRequest;
import spring.app.dto.SongResponse;
import spring.app.dto.SongsListResponse;
import spring.app.model.SongQueue;
import spring.app.service.CutSongService;
import spring.app.service.abstraction.*;
import spring.app.service.entity.Track;
import spring.app.util.PlayerPaths;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class TelegramServiceImpl implements TelegramService {
    private final MusicSearchService musicSearchService;
    private final CutSongService cutSongService;
    private final SongQueueService songQueueService;
    private final SongService songService;
    private final CompanyService companyService;
    private final LoadSongService loadSongService;
    private PlayerPaths playerPaths;

    public TelegramServiceImpl(MusicSearchService musicSearchService,
                               CutSongService cutSongService,
                               SongQueueService songQueueService,
                               SongService songService,
                               CompanyService companyService,
                               LoadSongService loadSongService,
                               PlayerPaths playerPaths) {
        this.musicSearchService = musicSearchService;
        this.cutSongService = cutSongService;
        this.songQueueService = songQueueService;
        this.songService = songService;
        this.companyService = companyService;
        this.loadSongService = loadSongService;
        this.playerPaths = playerPaths;
    }

    @Override
    public SongResponse getSong(SongRequest songRequest) throws IOException, BitstreamException,
            DecoderException {
        SongResponse songResponse = null;
        // Загружаем запрошенный трек из БД
        Long songId = songRequest.getSongId();
        Track track = loadSongService.getSong(songId);

        if (track != null) {
            String trackName = track.getFullTrackName();
            byte[] trackBytes = track.getTrack();
            // создаем 30-секундный отрезок для превью
            byte[] cutSong = cutSongService.сutSongMy(trackBytes, -1, 31);
            Long position = getPosition(songId, songRequest.getCompanyId());

            songResponse = new SongResponse(songRequest.getChatId(), songId, cutSong, trackName, position);
        }
        return songResponse;
    }

    @Override
    public SongsListResponse databaseSearch(SongRequest songRequest) throws IOException, BitstreamException, DecoderException {
        List<BotSongDto> songsList = songService.getBySearchRequests(songRequest.getAuthorName(), songRequest.getSongName());
        return new SongsListResponse(songsList == null ? Collections.emptyList() : songsList);
    }


    /**
     * Получаем с бота SongRequest с информацией о песне которую нужно найти для пользователя.
     * Ищем трек на сервисах.
     * Получаем полное название трека.
     * Создаем 30сек отрезок для бота.
     * Получаем id песни из базы после сохранения.
     * Записываем песню в директорию с именем по id песни в базе (например 1.mp3).
     * Определяем позицию в очереди.
     *
     * @param songRequest - SongRequest с информацией о песне которую нужно найти для пользователя
     * @return Возвращаем боту SongResponse с необходимой инфой
     * @throws IOException
     * @throws BitstreamException
     * @throws DecoderException
     */
    @Override
    public SongResponse servicesSearch(SongRequest songRequest) throws IOException, BitstreamException,
            DecoderException {
        SongResponse songResponse = null;
        // Ищем запрошенный трек в музыкальных сервисах
        Track track = musicSearchService.getSong(songRequest.getAuthorName(), songRequest.getSongName());

        if (track != null) {
            String trackName = track.getFullTrackName();
            byte[] trackBytes = track.getTrack();
            // создаем 30-секундный отрезок для превью
            byte[] cutSong = cutSongService.сutSongMy(trackBytes, -1, 31);
            Long songId;
            if (!songService.isExist(track.getSong())) {
                // получаем id песни после занесения в БД
                songId = musicSearchService.updateData(track);
                Path path = playerPaths.getSongDir(songId);
                try {
                    Files.write(path, track.getTrack());  //записываем песню в директорию
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                songId = songService.getSongIdByAuthorAndName(track.getAuthor(), track.getSong());
            }

            Long position = getPosition(songId, songRequest.getCompanyId());
            songResponse = new SongResponse(songRequest.getChatId(), songId, cutSong, trackName, position);
        }
        return songResponse;
    }

    private Long getPosition(Long songId, Long companyId) {
        // По позиции песни в очереди song_queue определяем position.
        // Если песни нет в очереди - отдаем боту position = 0L
        Long position;

        //Достаем очередь по песне и компании
        SongQueue songQueue = songQueueService.getSongQueueBySongAndCompany(
                songService.getById(songId),
                companyService.getById(companyId)
        );
        if (songQueue == null) {
            position = 0L;
        } else {
            position = songQueue.getPosition(); //сетим позицию песни в song_queue
        }

        return position;
    }
}
