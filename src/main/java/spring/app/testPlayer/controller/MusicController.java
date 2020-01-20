package spring.app.testPlayer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.app.testPlayer.model.Howl;
import spring.app.testPlayer.service.MusicService;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/music")
public class MusicController {

    @Autowired
    private MusicService musicService;

    @Value("${music.path}")
    private String musicPath;

    @GetMapping("/player")
    public String getMainPage(Model model) {
        String fullMusicPath = System.getProperty("user.dir") + "/" + musicPath;
        model.addAttribute("playList", musicService.getAllMusics().stream().map(music -> new Howl(music.getName(), fullMusicPath + music.getName(), null)).peek(System.out::println).collect(Collectors.toList()));
        return "player/player";
    }

    @GetMapping("/all")
    public String getMusicPage() {
        return "player/musicList";
    }
}
