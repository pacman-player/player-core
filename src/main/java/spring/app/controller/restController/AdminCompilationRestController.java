package spring.app.controller.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring.app.dto.SongCompilationDto;
import spring.app.model.SongCompilation;
import spring.app.service.abstraction.FileUploadService;
import spring.app.service.abstraction.SongCompilationService;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/admin/compilation")
public class AdminCompilationRestController {
    private SongCompilationService songCompilationService;
    private FileUploadService fileUploadService;

    @Autowired
    public AdminCompilationRestController(SongCompilationService songCompilationService,
                                          FileUploadService fileUploadService) {
        this.songCompilationService = songCompilationService;
        this.fileUploadService = fileUploadService;
    }

    @GetMapping
    public List<SongCompilation> getAllCompilation() {
        return songCompilationService.getAllSongCompilations();
    }

    @PostMapping("/update")
    public void updateCompilation(@ModelAttribute SongCompilationDto songCompilationDto) throws IOException {
        SongCompilation compilation = songCompilationService.getSongCompilationById(songCompilationDto.getId());

        if (songCompilationDto.getCover() != null) {
            String coverName = fileUploadService.upload(songCompilationDto.getCover());
            compilation.setCover(coverName);
        }
        compilation.setName(songCompilationDto.getName());

        songCompilationService.updateCompilation(compilation);
    }
}
