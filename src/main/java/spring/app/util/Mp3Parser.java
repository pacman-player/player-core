package spring.app.util;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
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

public class Mp3Parser {

    private final SongService songService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final SongCompilationService songCompilationService;
    private final MusicSearchService musicSearchService;

    public Mp3Parser(SongService songService,
                     AuthorService authorService,
                     GenreService genreService,
                     SongCompilationService songCompilationService,
                     MusicSearchService musicSearchService) {
        this.songService = songService;
        this.authorService = authorService;
        this.genreService = genreService;
        this.songCompilationService = songCompilationService;
        this.musicSearchService = musicSearchService;
    }

    public void apply(String path) throws InvalidDataException, IOException, UnsupportedTagException {

        FileFilter fileFilter = pathname -> pathname.getPath().endsWith(".mp3");
        File[] songs = new File(path).listFiles(fileFilter);

        for (int i = 0; i < songs.length; i++) {
            File song = songs[i];
            copyInitFile(song, i+1);
        }
    }

    public void copyInitFile(File file, int fileId) throws IOException {
        Path fileO = Paths.get("music/" + fileId + ".mp3");
        Files.copy(new FileInputStream(file), fileO, StandardCopyOption.REPLACE_EXISTING);
    }

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


        Path fileO = Paths.get("music/" + id + ".mp3");

        Files.copy(new FileInputStream(file), fileO, StandardCopyOption.REPLACE_EXISTING);
        return song;
    }
}