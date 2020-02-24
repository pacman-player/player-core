package spring.app.controller.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring.app.dto.SongCompilationDto;
import spring.app.dto.TestDto;
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

    @PutMapping("/update")
    public void updateCompilation(@RequestBody TestDto dto) throws IOException {
        System.out.println("\n\n" + dto.getId() + "\n\n");
//        SongCompilation compilation = songCompilationService.getSongCompilationById(dto.getId());

//        if (dto.getCover() != null) {
//            String coverName = fileUploadService.upload(dto.getCover());
//            compilation.setCover(coverName);
//        }
//        compilation.setName(dto.getName());
//        System.out.println("\n\n" + compilation + "\n\n");

//        songCompilationService.updateCompilation(compilation);
    }
}
