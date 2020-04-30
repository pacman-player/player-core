package spring.app.service.impl.musicSearcher;

import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.DecoderException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dto.SongRequest;
import spring.app.dto.SongResponse;
import spring.app.model.SongQueue;
import spring.app.service.CutSongService;
import spring.app.service.abstraction.*;
import spring.app.service.entity.Track;
import spring.app.util.PlayerPaths;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@Transactional
public class TelegramServiceImpl implements TelegramService {
    private final MusicSearchService musicSearchService;
    private final CutSongService cutSongService;
    private final SongQueueService songQueueService;
    private final SongService songService;
    private final CompanyService companyService;
    private PlayerPaths playerPaths;
    private Track track = null;
    private Long songId;

    public TelegramServiceImpl(MusicSearchService musicSearchService,
                               CutSongService cutSongService,
                               SongQueueService songQueueService,
                               SongService songService,
                               CompanyService companyService,
                               PlayerPaths playerPaths) {
        this.musicSearchService = musicSearchService;
        this.cutSongService = cutSongService;
        this.songQueueService = songQueueService;
        this.songService = songService;
        this.companyService = companyService;
        this.playerPaths = playerPaths;
    }

    @Override
    public SongResponse getSong(SongRequest songRequest) throws IOException, BitstreamException,
            DecoderException {
        if (track == null) {
            track = musicSearchService.getSong(songRequest.getAuthorName(), songRequest.getSongName());
            songId = musicSearchService.updateData(track);
        }
        String trackName = track.getFullTrackName();
        byte[] trackBytes = track.getTrack();

        SongResponse songResponse = new SongResponse(songRequest.getChatId(), songId, trackBytes, trackName);
        return songResponse;
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
    public SongResponse approveSong(SongRequest songRequest) throws IOException, BitstreamException,
            DecoderException {
        SongResponse songResponse = null;
        // Ищем запрошенный трек в музыкальных сервисах
        track = musicSearchService.getSong(songRequest.getAuthorName(), songRequest.getSongName());

        if (track != null) {
            String trackName = track.getFullTrackName();
            byte[] trackBytes = track.getTrack();
            // создаем 30-секундный отрезок для превью
            byte[] cutSong = cutSongService.сutSongMy(trackBytes, -1, 31);
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

            // По position определяем позицию песни в очереди song_queue.
            // Если песни нет в очереди - отдаем боту position = 0L
            Long position;

            //Достаем очередь по песне и компании
            SongQueue songQueue = songQueueService.getSongQueueBySongAndCompany(
                    songService.getById(songId),
                    companyService.getById(songRequest.getCompanyId())
            );
            if (songQueue == null) {
                position = 0L;
            } else {
                position = songQueue.getPosition(); //сетим позицию песни в song_queue которую ищем через бота
            }

            songResponse = new SongResponse(songRequest.getChatId(), songId, cutSong, trackName, position);
        }
        return songResponse;
    }
}
