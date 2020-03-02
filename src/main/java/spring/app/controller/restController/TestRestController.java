package spring.app.controller.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring.app.dao.abstraction.SongDao;
import spring.app.model.Song;

import java.util.List;


@RestController
@RequestMapping("/api/test")
public class TestRestController {
    private SongDao songDao;

    @Autowired
    public TestRestController(SongDao songDao) {
        this.songDao = songDao;
    }

    @GetMapping("/songs_with_genre/{genreId}")
    public List<Song> getAllWithGenreByGenreId(@PathVariable("genreId") Long id) {
        return songDao.getAllWithGenreByGenreId(id);
    }

}
