package spring.app.controller.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.app.model.Song;
import spring.app.service.abstraction.SongCompilationService;

import java.util.List;


@RestController
@RequestMapping("/api/test")
public class TestRestController {
    private SongCompilationService songCompilationService;

    @Autowired
    public TestRestController(SongCompilationService songCompilationService) {
        this.songCompilationService = songCompilationService;
    }

    @GetMapping("/compilation/content/{compilationId}")
    public List<Song> getAllWithGenreByGenreId(@PathVariable Long compilationId) {
        return songCompilationService.getSongCompilationContentById(compilationId);
    }

}
