package spring.app.controller;

import org.springframework.web.bind.annotation.*;
import spring.app.service.abstraction.MusicService;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
@RequestMapping("/api")
public class MusicRestController {
    private final MusicService musicService;

    public MusicRestController(MusicService musicService) {
        this.musicService = musicService;
    }

    @RequestMapping(value = "/download/{name}", method = RequestMethod.GET)
    public void download(@PathVariable("name") String musicName,HttpServletResponse response) throws IOException, ServletException {
            musicService.fileToStream(musicName, response);
    }
}