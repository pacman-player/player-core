package spring.app.service.impl;

import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.DecoderException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dto.SongRequest;
import spring.app.dto.SongResponse;
import spring.app.model.Author;
import spring.app.model.Genre;
import spring.app.model.Song;
import spring.app.service.CutSongService;
import spring.app.service.abstraction.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Service
@Transactional
public class TelegramServiceImpl implements TelegramService {

    private final ZaycevSaitServise zaycevSaitServise;
    private final CutSongService cutSongService;
    private final SongService songService;
    private final AuthorService authorService;
    private final GenreService genreService;


    public TelegramServiceImpl(ZaycevSaitServise zaycevSaitServise, CutSongService cutSongService, SongService songService, AuthorService authorService, GenreService genreService) {
        this.zaycevSaitServise = zaycevSaitServise;
        this.cutSongService = cutSongService;
        this.songService = songService;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @Override
    public SongResponse getSong(SongRequest songRequest) throws IOException {
        byte[] bytes = zaycevSaitServise.getSong(songRequest.getAuthorName(),songRequest.getSongName());
        SongResponse songResponse = new SongResponse(songRequest.getChatId(), songRequest.getSongId(), bytes,
                songRequest.getAuthorName()+ " - " + songRequest.getSongName());
        return songResponse;
    }

    @Override
    public SongResponse approveSong(SongRequest songRequest) throws IOException, BitstreamException, DecoderException {
        //Необходимо в дальнейшем рассмотреть необходимость добавления этих ентити в базу
        //Они поидее должны добавляться автоматически при добавлении песни
        //Так же рассмотреть возможность взять жанр из метадаты mp3 файла
        Author author = new Author(songRequest.getAuthorName());
        Genre genre = new Genre("userSong");
        Song songToAdd = new Song(songRequest.getSongName(), author, genre);
        authorService.addAuthor(author);
        genreService.addGenre(genre);
        songService.addSong(songToAdd);
        Song song = songService.getByName(songToAdd.getName());
        byte[] bytes = zaycevSaitServise.getSong(songRequest.getAuthorName(),songRequest.getSongName());
        byte[] cutSong = cutSongService.сutSongMy(bytes, -1, 31);
        SongResponse songResponse = new SongResponse(songRequest.getChatId(), song.getId(), cutSong,
                songRequest.getAuthorName()+ " - " + songRequest.getSongName());
        return songResponse;
    }
}
