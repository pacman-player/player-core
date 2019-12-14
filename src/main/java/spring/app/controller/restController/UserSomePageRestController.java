package spring.app.controller.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import spring.app.service.abstraction.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/user/somePage")
public class UserSomePageRestController {

    private final SearchService searchService;
    private final FileUploadService fileUploadService;

    @Autowired
    public UserSomePageRestController(SearchService searchService, FileUploadService fileUploadService) {
        this.searchService = searchService;
        this.fileUploadService = fileUploadService;
    }

    @PostMapping("/search")
    public void search(@RequestParam String artist, @RequestParam String track) {
        searchService.search(artist, track);
    }

    @PostMapping("/fileUpload")
    public ResponseEntity<String> fileUpload(
            @RequestParam("songAuthor") String songAuthor,
            @RequestParam("songGenre") String songGenre,
            @RequestParam("songName") String songName,
            @RequestParam("file") MultipartFile file) throws UnsupportedEncodingException {
        return fileUploadService.upload(songAuthor, songGenre, songName, file);
    }
}