package spring.app.controller.restController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.app.model.Song;
import spring.app.model.SongQueue;
import spring.app.model.User;
import spring.app.service.abstraction.SongQueueService;
import spring.app.service.abstraction.SongService;
import spring.app.service.abstraction.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/user/song")
public class UserSongRestController {
    private final static Logger LOGGER = LoggerFactory.getLogger(UserSongRestController.class);
    private SongService songService;
    private UserService userService;
    private SongQueueService songQueueService;

    @Autowired
    public UserSongRestController(SongService songService, UserService userService, SongQueueService songQueueService) {
        this.songService = songService;
        this.userService = userService;
        this.songQueueService = songQueueService;
    }

    @GetMapping(value = "/get/all-song/song-compilation/{id}")
    public List<Song> getAllSongInSongCompilation(@PathVariable("id") Long id) {
        LOGGER.info("GET request '/get/all-song/song-compilation/{}'", id);
        List<Song> list = songService.getAllSongInSongCompilation(id);
        LOGGER.info("Result has {} lines", list.size());
        return list;
    }

    @GetMapping("/songsInQueue")
    public List<Song> getSongsInSongQueueOfCompany() {
        LOGGER.info("GET request '/songsInQueue'");
        User authUser = userService.getUserById(userService.getIdAuthUser());
        Long companyId = authUser.getCompany().getId();
        List<SongQueue> songQueues = songQueueService.getByCompanyId(companyId);
        LOGGER.info("Logged-in User has {} lines in SongQueue list", songQueues.size());
        if (!songQueues.isEmpty()) {
            songQueueService.deleteById(songQueues.get(0).getId());
            return Arrays.asList(songQueues.get(0).getSong());
        } else {
            return new ArrayList<>();
        }
    }
}
