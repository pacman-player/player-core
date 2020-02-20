package spring.app.controller.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.app.model.SongCompilation;
import spring.app.service.abstraction.SongCompilationService;

import java.util.List;


@RestController
@RequestMapping("/api/admin/compilation")
public class AdminCompilationRestController {
    private SongCompilationService songCompilationService;

    @Autowired
    public AdminCompilationRestController(SongCompilationService songCompilationService) {
        this.songCompilationService = songCompilationService;
    }

    @GetMapping
    public List<SongCompilation> getAllCompilation() {
        return songCompilationService.getAllSongCompilations();
    }
}
