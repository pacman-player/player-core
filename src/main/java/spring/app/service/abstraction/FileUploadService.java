package spring.app.service.abstraction;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;

public interface FileUploadService {
    ResponseEntity<String> upload(String songAuthor, String songGenre, String songName, MultipartFile file) throws UnsupportedEncodingException;
}
