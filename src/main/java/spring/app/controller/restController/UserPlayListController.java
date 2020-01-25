package spring.app.controller.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring.app.model.SongCompilation;
import spring.app.service.abstraction.SongCompilationService;

import java.util.List;

@RestController
@RequestMapping("/api/user/play-list")
public class UserPlayListController {
    private SongCompilationService songCompilationService;

    @Autowired
    public UserPlayListController(SongCompilationService songCompilationService) {
        this.songCompilationService = songCompilationService;
    }

    @GetMapping(value = "/morning-playlist/add/song-compilation/{id}")
    public void addSongCompilationToMorningPlaylist(@PathVariable Long id) {
        songCompilationService.addSongCompilationToMorningPlaylist(id);
    }

    @GetMapping(value = "/midday-playlist/add/song-compilation/{id}")
    public void addSongCompilationToMiddayPlaylist(@PathVariable Long id) {
        songCompilationService.addSongCompilationToMiddayPlaylist(id);
    }

    @GetMapping(value = "/evening-playlist/add/song-compilation/{id}")
    public void addSongCompilationToEveningPlaylist(@PathVariable("id") Long id) {
        songCompilationService.addSongCompilationToEveningPlaylist(id);
    }

    @GetMapping(value = "/morning-playlist/get/all-song-compilation/company/{id}")
    public List<SongCompilation> getAllCompilationsInMorningPlaylist(@PathVariable("id") Long id) {
        return songCompilationService.getAllCompilationsInMorningPlaylistByCompanyId(id);
    }

    @GetMapping(value = "/midday-playlist/get/all-song-compilation/company/{id}")
    public List<SongCompilation> getAllCompilationsInMiddayPlaylist(@PathVariable("id") Long id) {
        return songCompilationService.getAllCompilationsInMiddayPlaylistByCompanyId(id);
    }

    @GetMapping(value = "/evening-playlist/get/all-song-compilation/company/{id}")
    public List<SongCompilation> getAllCompilationsInEveningPlaylist(@PathVariable("id") Long id) {
        return songCompilationService.getAllCompilationsInEveningPlaylistByCompanyId(id);
    }
}
