package spring.app.controller.restController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final static Logger LOGGER = LoggerFactory.getLogger(AdminCompilationRestController.class);

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
        LOGGER.info("GET request '/'");
        List<SongCompilation> compilations = songCompilationService.getAllSongCompilations();
        LOGGER.info("Result has {} lines", compilations.size());
        return compilations;
    }

    @PostMapping("/update")
    public void updateCompilation(@ModelAttribute SongCompilationDto songCompilationDto) throws IOException {
        LOGGER.info("POST request '/update' to update SongCompilation id = {}", songCompilationDto.getId());
        SongCompilation compilation = songCompilationService.getSongCompilationById(songCompilationDto.getId());
        LOGGER.info("SongCompilation [name = '{}'; id = {}] found", compilation.getName(), compilation.getId());

        if (songCompilationDto.getCover() != null
                && fileUploadService.isImage(songCompilationDto.getCover().getOriginalFilename())) {
            String coverName = fileUploadService.upload(songCompilationDto.getCover());
            LOGGER.info("Cover '{}' uploaded", coverName);
            // Удалить текущую обложку
            if (compilation.getCover() != null) {
                fileUploadService.eraseCurrentFile(compilation.getCover());
                LOGGER.info("Previous cover deleted");
            }

            compilation.setCover(coverName);
            LOGGER.info("Cover for SongCompilation with id = {} updated", compilation.getId());
        }
        compilation.setName(songCompilationDto.getName());

        songCompilationService.updateCompilation(compilation);
        LOGGER.info("SongCompilation with id = {} updated successfully", compilation.getId());
    }
}
