package spring.app.service.abstraction;

import org.springframework.http.ResponseEntity;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface MusicService {
    ServletOutputStream fileToStream(String soundName, HttpServletResponse response) throws ServletException, IOException;

    ResponseEntity playMusic(String musicAuthor, String musicTitle);

    ResponseEntity albumsCover(String musicAuthor, String musicTitle);
}
