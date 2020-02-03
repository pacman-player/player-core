package spring.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.app.service.abstraction.MusicService;

@RestController
@RequestMapping("/api/music")
public class MusicRestController {
    private final MusicService musicService;

    public MusicRestController(MusicService musicService) {
        this.musicService = musicService;
    }

    @GetMapping("/play/{musicTitle}")
    public ResponseEntity playMusic(@PathVariable String musicTitle) {
        return musicService.playMusic(musicTitle);
    }
}