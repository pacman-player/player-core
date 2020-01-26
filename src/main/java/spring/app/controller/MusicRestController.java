package spring.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.app.service.abstraction.MusicService;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

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