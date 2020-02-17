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
    private Track track = null;
    private Long songId;

    private final MusicSearchService musicSearchService;
    private final CutSongService cutSongService;
    private final SongQueueService songQueueService;
    private final SongService songService;
    private final CompanyService companyService;

    public TelegramServiceImpl(MusicSearchService musicSearchService, CutSongService cutSongService, SongQueueService songQueueService, SongService songService, CompanyService companyService) {
        this.musicSearchService = musicSearchService;
        this.cutSongService = cutSongService;
        this.songQueueService = songQueueService;
        this.songService = songService;
        this.companyService = companyService;
    }

    @Override
    public SongResponse getSong(SongRequest songRequest) throws IOException {
        if(track == null) {
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
     * Создаем 30сек отрезок для бота
     * Получаем id песни из базы после сохранения
     * Записываем песню в директорию с именем по id песни в базе (например 1.mp3)
     * Определяем позицию искомой песни в song_queue
     * Возвращаем боту SongResponce с необходимой инфой.
     * @param songRequest
     * @return
     * @throws IOException
     * @throws BitstreamException
     * @throws DecoderException
     */
    @Override
    public SongResponse approveSong(SongRequest songRequest) throws IOException, BitstreamException, DecoderException {
        //in this line the track is being downloaded and saved to hd (/music)
        track = musicSearchService.getSong(songRequest.getAuthorName(),songRequest.getSongName());
        String trackName = track.getFullTrackName();
        byte[] trackBytes = track.getTrack();
        //cut song here
        byte[] cutSong = cutSongService.сutSongMy(trackBytes, -1, 31);
        //setting songId vis going back to music
        songId = musicSearchService.updateData(track);
        Path path = PlayerPaths.getSongsDir(songId + ".mp3");
        if (track.getPath() != null) {
            try {
                Files.write(path, track.getTrack());  //записываем песню с директорию
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //По position определяем позицию песни в очереди song_queue. Если песни нет в очереди - отдаем боту position = 0L
        Long position = 0L;

        //Достаем очередь по песне и компании
        SongQueue songQueueBySongAndCompany = songQueueService.getSongQueueBySongAndCompany(songService.getSongById(songId), companyService.getById(songRequest.getCompanyId()));
        if (songQueueBySongAndCompany != null) {
            position = songQueueBySongAndCompany.getPosition(); //сетим позицию песни в song_queue которую ищем через бота
        }

        SongResponse songResponse = new SongResponse(songRequest.getChatId(), songId, cutSong, trackName, position);
        return songResponse;
    }
}
