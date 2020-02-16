package spring.app.controller.restController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import spring.app.service.abstraction.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/user/somePage")
public class UserSomePageRestController {
    private final static Logger LOGGER = LoggerFactory.getLogger(UserSomePageRestController.class);
    private final FileUploadService fileUploadService;

    @Autowired
    public UserSomePageRestController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @PostMapping("/fileUpload")
    public ResponseEntity<String> fileUpload(
            @RequestParam("songAuthor") String songAuthor,
            @RequestParam("songGenre") String songGenre,
            @RequestParam("songName") String songName,
            @RequestParam("file") MultipartFile file) throws UnsupportedEncodingException {
        LOGGER.info("POST request '/fileUpload' with Song Author = {}, Name = {}, Genre = {}",
                    songAuthor,
                    songName,
                    songGenre);
        try {
            ResponseEntity<String> result = fileUploadService.upload(
                    songAuthor,
                    songGenre,
                    songName,
                    file);
            LOGGER.info("Success!");
            return result;
        } catch (UnsupportedEncodingException e){
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
    }
}