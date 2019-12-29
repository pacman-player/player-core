package spring.app.controller;

import org.springframework.web.bind.annotation.*;
import spring.app.service.abstraction.MusicSearchAndStorage;
import spring.app.service.abstraction.MusicService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
@RequestMapping("/api")
public class MusicRestController {
    private final MusicService musicService;
    private final MusicSearchAndStorage musicSearchAndStorage;

    public MusicRestController(MusicService musicService, MusicSearchAndStorage musicSearchAndStorage) {
        this.musicService = musicService;
        this.musicSearchAndStorage = musicSearchAndStorage;
    }

    @RequestMapping(value = "/download/{name}", method = RequestMethod.GET)
    public void download(@PathVariable("name") String musicName,HttpServletResponse response) throws IOException, ServletException {
            musicService.fileToStream(musicName, response);
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public void search(@RequestParam("auth") String authName,@RequestParam("song") String songName,HttpServletResponse response) throws IOException, ServletException {
        musicSearchAndStorage.searchBySongNameAndAuthorName(songName,authName);
    }
}