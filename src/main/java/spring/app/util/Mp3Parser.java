package spring.app.util;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import spring.app.model.Song;
import spring.app.model.SongCompilation;
import spring.app.service.abstraction.*;


import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.Set;

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
        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getPath().endsWith(".mp3");
            }
        };
        SongCompilation songCompilation1 = new SongCompilation();
        songCompilation1.setName("My compilation1");
        SongCompilation songCompilation2 = new SongCompilation();
        songCompilation2.setName("My compilation2");
        SongCompilation songCompilation3 = new SongCompilation();
        songCompilation3.setName("My compilation3");
        SongCompilation songCompilation4 = new SongCompilation();
        songCompilation4.setName("My compilation4");
        Set<Song> songList1 = new HashSet<>();
        Set<Song> songList2 = new HashSet<>();
        Set<Song> songList3 = new HashSet<>();
        Set<Song> songList4 = new HashSet<>();
        File[] songs = new File(path).listFiles(fileFilter);
        System.out.println(songs.length);
        for (int i = 0; i < songs.length; i++) {
            File song = songs[i];
            System.out.println(song);
            System.out.println(song.getPath());
            System.out.println();
            Mp3File mp3File = new Mp3File(song);
            System.out.println(song);
            Song s = readTags(song);
            if (i < 3) {
                songList2.add(s);
            } else if (i < 6) {
                songList3.add(s);
            }
            else if (i < 9) {
                songList1.add(s);
            } else {
                songList4.add(s);
            }

        }
        songCompilation1.setSong(songList1);
        songCompilation1.setGenre(genreService.getByName("Pop"));
        songCompilationService.addSongCompilation(songCompilation1);
        songCompilation2.setSong(songList2);
        songCompilation2.setGenre(genreService.getByName("Pop"));
        songCompilationService.addSongCompilation(songCompilation2);
        songCompilation3.setSong(songList3);
        songCompilation3.setGenre(genreService.getByName("Soundtrack"));
        songCompilationService.addSongCompilation(songCompilation3);
        songCompilation4.setSong(songList4);
        songCompilation4.setGenre(genreService.getByName("Alternative"));
        songCompilationService.addSongCompilation(songCompilation4);
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