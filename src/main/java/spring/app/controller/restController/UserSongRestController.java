package spring.app.controller.restController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.app.model.Song;
import spring.app.service.abstraction.SongService;

import java.util.List;

@RestController
@RequestMapping("/api/user/song")
public class UserSongRestController {
    private final static Logger LOGGER = LoggerFactory.getLogger(UserSongRestController.class);
    private SongService songService;

    @Autowired
    public UserSongRestController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping(value = "/get/all-song/song-compilation/{id}")
    public List<Song> getAllSongInSongCompilation(@PathVariable("id") Long id) {
        List<Song> list = songService.getAllSongInSongCompilation(id);
        LOGGER.info("GET request '/get/all-song/song-compilation/{}'. Result has {} lines", id, list.size());
        return list;
    }
}
