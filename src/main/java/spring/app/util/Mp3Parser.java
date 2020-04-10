package spring.app.util;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import spring.app.model.Song;
import spring.app.service.abstraction.*;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

@Component
public class Mp3Parser {

    private final SongService songService;
    private final MusicSearchService musicSearchService;
    private PlayerPaths playerPaths;

    @Value("${music.path}")
    private String musicPath;

    public Mp3Parser(SongService songService,
                     MusicSearchService musicSearchService,
                     PlayerPaths playerPaths) {
        this.songService = songService;
        this.musicSearchService = musicSearchService;
        this.playerPaths = playerPaths;
    }


    public void apply(String path) throws InvalidDataException, IOException, UnsupportedTagException {

        FileFilter fileFilter = pathname -> pathname.getPath().endsWith(".mp3");
        File[] songs = new File(path).listFiles(fileFilter);
        System.out.println(Arrays.asList(songs));

        for (File song : songs) {
            copyInitSongs(song);
        }
    }

    /**
     * Метод для простого копирования файлов из /music1/ в /music/
     */
    public void copyInitFile(File file, int fileId) throws IOException {
        Path fileO = Paths.get(musicPath + fileId + ".mp3");
        Files.copy(new FileInputStream(file), fileO, StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * Метод для чтения тегов и поиска информации через музыкальные сервисы на старте программы. Использовать только один из методов.
     */
    public Song readTags(File file) throws InvalidDataException, IOException, UnsupportedTagException {
        String name = null;
        String author = null;
        String genre = null;
        byte[] image = null;

        Mp3File mp3File = new Mp3File(file);
        if (mp3File.hasId3v2Tag()) {
            ID3v2 id3v2Tag = mp3File.getId3v2Tag();
            name = id3v2Tag.getTitle();
            author = id3v2Tag.getArtist();
            genre = id3v2Tag.getGenreDescription();
            image = id3v2Tag.getAlbumImage();
        }

        String fullTrackName = author + " " + name;

        Long id = musicSearchService.updateData(fullTrackName, author, name);
        Song song = songService.getSongById(id);
       /*
       // Занесение init-треков в базу с упрощенной логикой, без определения жанров через MusicSearchService
       Author auth = new Author(author);
        Genre gen = genreService.getByName(genre);
        if(gen == null){
            gen = new Genre(genre);
            genreService.addGenre(gen);
        }
        authorService.addAuthor(auth);
        Song song = new Song(name, auth, gen);
        song.setName(name);
        song.setAuthor(auth);
        song.setGenre(gen);
        songService.addSong(song);
        Path fileO = Paths.get("music/" + songService.getByName(song.getName()).getId() + ".mp3");
        */


        Path fileO = Paths.get(musicPath + id + ".mp3");

        Files.copy(new FileInputStream(file), fileO, StandardCopyOption.REPLACE_EXISTING);
        return song;
    }

    public void copyInitSongs(File file) throws InvalidDataException, IOException, UnsupportedTagException {
        String name = null;
        String author = null;

        Mp3File mp3File = new Mp3File(file);
        if (mp3File.hasId3v2Tag()) {
            ID3v2 id3v2Tag = mp3File.getId3v2Tag();
            name = id3v2Tag.getTitle();
            author = id3v2Tag.getArtist();
        }
        Long songId = songService.getSongIdByAuthorAndName(author, name);
        Path pathToSaveFile = playerPaths.getSongDir(songId);
        Files.copy(new FileInputStream(file), pathToSaveFile, StandardCopyOption.REPLACE_EXISTING);
    }
}