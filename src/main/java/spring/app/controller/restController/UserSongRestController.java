package spring.app.controller.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.app.model.Company;
import spring.app.model.Song;
import spring.app.model.SongQueue;
import spring.app.model.User;
import spring.app.service.abstraction.SongQueueService;
import spring.app.service.abstraction.SongService;
import spring.app.service.abstraction.UserService;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user/song")
public class UserSongRestController {
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
        return songService.getAllSongInSongCompilation(id);
    }

    @GetMapping("/songsInQueue")
    public List<Song> getSongsInSongQueueOfCompany() {
        User authUser = userService.getUserById(userService.getIdAuthUser());
        Company company = authUser.getCompany();
        Set<SongQueue> songQueues = company.getSongQueues();
        songQueueService.deleteAllSongQueues(songQueues);
        return songQueues
                .stream()
                .sorted(Comparator.comparingLong(SongQueue::getPosition))
                .map(SongQueue::getSong).collect(Collectors.toList());
    }
}
