package spring.app.testPlayer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import spring.app.testPlayer.model.Music;
import spring.app.testPlayer.repository.MusicRepository;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MusicService {
    @Autowired
    private MusicRepository musicRepository;

    @Value("${music.path}")
    private String musicPath;

    public List<String> saveMusics(MultipartFile[] multipartFiles) {
        List<String> musicList = new ArrayList<>();
        Arrays.stream(multipartFiles).forEach(multipartFile -> {
            String musicName = saveMusic(multipartFile);
            if (musicName != null) {
                musicList.add(musicName);
            }
        });
        return musicList;
    }

    public void deleteMusicById(int id) {
        File file = new File(musicPath + getMusicById(id).getName());
        if (file.exists()) {
            file.delete();
        }
        musicRepository.delete(id);
    }

    public String saveMusic(MultipartFile multipartFile) {
        String musicTitle = multipartFile.getOriginalFilename();
        File file = new File(musicPath + musicTitle);
        if (!file.exists()) {
            try (FileOutputStream fileOutputStream = new FileOutputStream(musicPath + musicTitle)) {
                fileOutputStream.write(multipartFile.getBytes());
                musicRepository.save(new Music(musicTitle.replace(".mp3", "")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return musicTitle;
        }
        return null;
    }

    public List<Music> getAllMusics() {
        return musicRepository.findAll();
    }

    public Music getMusicById(int id) {
        return musicRepository.findOne(id);
    }

    public Music getMusicByName(String name) {
        return musicRepository.findByName(name);
    }

    public ServletOutputStream streamMusic(String musicName, HttpServletResponse response) throws ServletException, IOException {
        ServletOutputStream stream = null;
        BufferedInputStream buf = null;
        try {

            File mp3 = new File(musicPath + musicName + ".mp3");

            //set response headers
            stream = response.getOutputStream();
            response.setContentType("audio/mpeg");

            response.addHeader("Content-Disposition", "attachment; filename=" + musicName);

            response.setContentLength((int) mp3.length());

            FileInputStream input = new FileInputStream(mp3);
            buf = new BufferedInputStream(input);
            int readBytes = 0;
            //read from the file; write to the ServletOutputStream
            while ((readBytes = buf.read()) != -1)
                stream.write(readBytes);
            return stream;
        } catch (IOException ioe) {
            throw new ServletException(ioe.getMessage());
        } finally {
            if (stream != null)
                stream.close();
            if (buf != null)
                buf.close();
        }
    }

}
