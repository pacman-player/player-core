package spring.app.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.Main;
import spring.app.model.Author;
import spring.app.model.Song;
import spring.app.service.abstraction.AuthorService;
import spring.app.service.abstraction.FindMusicInDBService;
import spring.app.service.abstraction.SongService;
import spring.app.service.entity.Track;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Сервис осуществляет поиск песни и автора в базе данных(БД)
 * В случае наличия в БД обоих записей производит поиск песни в директории
 * где хранятся песни. Если песня есть, то она преобразуется в массив байтов.
 * Имея данные: автор, исполнитель, полное название и аудиофайл в ввиде массива байтов - создаем сущность Track
 */
@Service
@Transactional
public class FindMusicInDBServiceImpl implements FindMusicInDBService {

    private Track track;
    private String authorName;
    private String songName;
    private String trackName;

    private final AuthorService authorService;
    private final SongService songService;

    public FindMusicInDBServiceImpl(AuthorService authorService, SongService songService) {
        this.authorService = authorService;
        this.songService = songService;
    }

    @Override
    public Track findTrackFromDB(String trackName) throws IOException {
        this.trackName = trackName;
        byte[] trackBytes;
        if (trackName.contains(" – ")) {
            String[] perfomerAndSong = trackName.split(" – ");
            authorName = perfomerAndSong[0];
            songName = perfomerAndSong[1];
        }

        Author author = authorService.getByName(authorName); // получить автора из БД если есть с таким именем.
        Song song = songService.getByName(songName);          // получить  песню из БД если есть с таким именем.

        if (song == null || !song.getAuthor().equals(author)) {
            return null; // если автора или песни нет то возвращаем null, а также массив меньше 1Мб
        } else trackBytes = getTrackBytes(trackName);
            return new Track(authorName, songName, trackName, trackBytes);
    }

    byte[] getTrackBytes(String trackName) throws IOException {

        byte[] trackBytes = null;
        Path pathDownload = null;
        final String separator = File.separator;
        /**определяем путь до папки с песнями*/
        try {
            Path pathProject = (Paths.get(Main.class.getResource(".").toURI())).getParent().getParent();
            pathDownload = Paths.get(pathProject.getParent().getParent().toString() + separator + "resources" + separator + "songs");


        /**смотрим есть ли указанный файл размером более 1Мб, если да то получаем песню в ввиде массива байтов*/
        FileInputStream fin = new FileInputStream(String.valueOf(Paths.get(String.format("%s%s%s", pathDownload, separator, trackName + ".mp3"))));
        if (fin.available() > 1024 * 10) {
            trackBytes = new byte[fin.available()];
            fin.read(trackBytes, 0, fin.available());
        }
        } catch (URISyntaxException | FileNotFoundException ex) {
            ex.getMessage();
        }
        return trackBytes;

    }
}
