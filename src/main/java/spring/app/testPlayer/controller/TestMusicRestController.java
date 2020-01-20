package spring.app.testPlayer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import spring.app.testPlayer.model.Music;
import spring.app.testPlayer.service.MusicService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/music")
public class TestMusicRestController {
    @Autowired
    private MusicService musicService;

    @Value("${music.path}")
    private String musicPath;

    @PostMapping("/save")
    public List<String> saveMusics(@RequestParam MultipartFile[] musics) {
        return musicService.saveMusics(musics);
    }

    @GetMapping("/delete/{id}")
    public void deleteMusicById(@PathVariable int id) {
        musicService.deleteMusicById(id);
    }

    @GetMapping("/all")
    public List<Music> getAllMusics() {
        return musicService.getAllMusics();
    }

    @GetMapping("/musicPath")
    public String getMusicPath() {
        return System.getProperty("user.dir") + "/" + musicPath;
    }

    @GetMapping("/stream/{musicTitle}")
    public void streamMusic(@PathVariable String musicTitle, HttpServletResponse response) {
        try {
            musicService.streamMusic(musicTitle, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}
