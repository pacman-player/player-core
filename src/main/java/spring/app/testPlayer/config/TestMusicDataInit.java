package spring.app.testPlayer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import spring.app.testPlayer.model.Music;
import spring.app.testPlayer.repository.MusicRepository;
import spring.app.testPlayer.service.MusicService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TestMusicDataInit {
    @Value("${music.path}")
    private String musicPath;
    @Autowired
    private MusicService musicService;
    @Autowired
    private MusicRepository musicRepository;

    public void init() {
        File musicDirectory = new File(musicPath);
        if (!musicDirectory.exists()) {
            musicDirectory.mkdir();
            musicRepository.deleteAll();
        } else {
            List<Music> musicList = musicRepository.findAll();
            List<Music> absentMusicList = musicList.stream()
                    .filter(music -> {
                        File file = new File(musicPath + music.getName() + ".mp3");
                        return !file.exists();
                    }).collect(Collectors.toList());
            musicRepository.deleteInBatch(absentMusicList);
        }
    }
}
