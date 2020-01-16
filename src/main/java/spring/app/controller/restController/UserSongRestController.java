package spring.app.controller.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.app.model.Song;
import spring.app.service.abstraction.SongService;
import spring.app.util.HibernateInterceptor;
import spring.app.util.QueryCounter;

import java.util.List;

@RestController
@RequestMapping("/api/user/song")
public class UserSongRestController {
    private SongService songService;

    @Autowired
    public UserSongRestController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping(value = "/get/all-song/song-compilation/{id}")
    public List<Song> getAllSongInSongCompilation(@PathVariable("id") Long id) {
        return songService.getAllSongInSongCompilation(id);
    }
}
