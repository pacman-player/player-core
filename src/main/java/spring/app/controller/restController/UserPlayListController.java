package spring.app.controller.restController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.app.model.SongCompilation;
import spring.app.service.abstraction.SongCompilationService;

import java.util.List;

@RestController
@RequestMapping("/api/user/play-list")
public class UserPlayListController {
    private final Logger LOGGER = LoggerFactory.getLogger("UserPlayListController");
    private SongCompilationService songCompilationService;

    @Autowired
    public UserPlayListController(SongCompilationService songCompilationService) {
        this.songCompilationService = songCompilationService;
    }

    @GetMapping(value = "/morning-playlist/add/song-compilation/{id}")
    public void addSongCompilationToMorningPlaylist(@PathVariable("id") Long id) {
        songCompilationService.addSongCompilationToMorningPlaylist(id);
    }

    @GetMapping(value = "/midday-playlist/add/song-compilation/{id}")
    public void addSongCompilationToMiddayPlaylist(@PathVariable("id") Long id) {
        songCompilationService.addSongCompilationToMiddayPlaylist(id);
    }

    @GetMapping(value = "/evening-playlist/add/song-compilation/{id}")
    public void addSongCompilationToEveningPlaylist(@PathVariable("id") Long id) {
        songCompilationService.addSongCompilationToEveningPlaylist(id);
    }

    @GetMapping(value = "/morning-playlist/get/all-song-compilation")
    public List<SongCompilation> getAllCompilationsInMorningPlaylist() {
        return songCompilationService.getAllCompilationsInMorningPlaylist();
    }

    @GetMapping(value = "/midday-playlist/get/all-song-compilation")
    public List<SongCompilation> getAllCompilationsInMiddayPlaylist() {
        return songCompilationService.getAllCompilationsInMiddayPlaylist();
    }

    @GetMapping(value = "/evening-playlist/get/all-song-compilation")
    public List<SongCompilation> getAllCompilationsInEveningPlaylist() {
        return songCompilationService.getAllCompilationsInEveningPlaylist();
    }
}
