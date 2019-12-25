package spring.app.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import spring.app.model.Author;
import spring.app.model.Genre;
import spring.app.model.Song;
import spring.app.service.abstraction.AuthorService;
import spring.app.service.abstraction.DownloadMusicVkRuService;
import spring.app.service.abstraction.GenreService;
import spring.app.service.abstraction.SongService;
import spring.app.util.PlayerPaths;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
public class DownloadMusicVkRuServiceImpl implements DownloadMusicVkRuService {

    //@Value("${uploaded_files_path}")
    private String fileFolder;

    private final GenreService genreService;
    private final AuthorService authorService;
    private final SongService songService;

    @Autowired
    public DownloadMusicVkRuServiceImpl(GenreService genreService, AuthorService authorService, SongService songService) {
        this.genreService = genreService;
        this.authorService = authorService;
        this.songService = songService;
    }

    @Override
    public ResponseEntity<String> search(String artist, String track) throws IOException {

        final String SEARCH_BASE_URL = "https://downloadmusicvk.ru/audio/search?q=";
        boolean success = false;
        boolean found = false;

        if (track.isEmpty()) {
            return new ResponseEntity<>(encode("Введите название песни"), HttpStatus.BAD_REQUEST);
        }

        if (songService.isExist(track)) {
            return new ResponseEntity<>(encode("Файл с таким названием существует в базе"), HttpStatus.BAD_REQUEST);
        }

        String searchUrl = SEARCH_BASE_URL + artist.trim().toLowerCase().replaceAll("\\s", "+") +
                "+" + track.trim().toLowerCase().replaceAll("\\s", "+");

        Document document = Jsoup.connect(searchUrl).get();
        if (document == null) {
            return new ResponseEntity<>(encode("Нет коннекта с сайтом"), HttpStatus.BAD_REQUEST);
        }

        Elements aElements = document.getElementsByAttributeValue("class", "btn btn-primary btn-xs download");

        for (Element aElement : aElements) {
            String[] arr = aElement.attr("href").substring(19).split("&");
            if (arr.length < 6) {
                continue;
            }

            if (!decode(arr[1].substring(7)).replaceAll("\\+", " ").toLowerCase().contains(artist.trim().toLowerCase()) ||
                    !decode(arr[2].substring(6)).replaceAll("\\+", " ").toLowerCase().contains(track.trim().toLowerCase())) {
                found = false;
                continue;
            }

            found=true;
            if (artist.isEmpty()) {
                artist = decode(arr[1].substring(7)).replaceAll("\\+", " ").toLowerCase();
            }
            String downloadUrl = "https://downloadmusicvk.ru/audio/download?" +
                    arr[1] + "&" + arr[2] + "&" + arr[5] + "&" + arr[0];

            URL obj = new URL(downloadUrl);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream in = con.getInputStream();
                String fileName = addToDatabase(artist, track);
                //Path path = Paths.get(fileFolder + fileName);
                Path path = PlayerPaths.getSongsDir(fileName);
                if (path != null) {
                    Files.deleteIfExists(path);
                    Files.copy(in, Files.createFile(path), REPLACE_EXISTING);
                    in.close();
                    success = true;
                }
                break;
            }
        }

        if (!found) {
            return new ResponseEntity<>(encode("Нет совпадений по поиску"), HttpStatus.BAD_REQUEST);
        }

        if (!success) {
            return new ResponseEntity<>(encode("Не возможно загрузить по этому запросу"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(encode("Файл загружен"), HttpStatus.OK);
    }

    private String addToDatabase(String artist, String track) {
        Genre genre = genreService.getByName("undefined");
        if (genre == null) {
            genreService.addGenre(new Genre("undefined"));
            genre = genreService.getByName("undefined");
        }

        Author author = authorService.getByName(artist);
        if (author == null) {
            Author author1 = new Author(artist);
            authorService.addAuthor(author1);
            author = authorService.getByName(artist);
        }

        Song song = new Song(track);
        song.setAuthor(author);
        song.setGenre(genre);
        songService.addSong(song);
        song = songService.getByName(track);

        return author.getId() + "_" + song.getId() + ".mp3";
    }

    private String encode(String line) throws UnsupportedEncodingException {
        return URLEncoder.encode(line, "UTF-8")
                .replaceAll("\\+", "%20")
                .replaceAll("\\%21", "!")
                .replaceAll("\\%27", "'")
                .replaceAll("\\%28", "(")
                .replaceAll("\\%29", ")")
                .replaceAll("\\%7E", "~");
    }

    private String decode(String line) throws UnsupportedEncodingException {
        return URLDecoder.decode(URLDecoder.decode(line, "UTF-8"), "UTF-8");
    }
}