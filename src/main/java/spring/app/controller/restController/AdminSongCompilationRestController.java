package spring.app.controller.restController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import spring.app.dto.SongCompilationDto;
import spring.app.model.Song;
import spring.app.model.SongCompilation;
import spring.app.service.abstraction.FileUploadService;
import spring.app.service.abstraction.GenreService;
import spring.app.service.abstraction.SongCompilationService;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/admin/compilation")
public class AdminSongCompilationRestController {
    private final static Logger LOGGER = LoggerFactory.getLogger(AdminSongCompilationRestController.class);

    private final SongCompilationService songCompilationService;
    private final FileUploadService fileUploadService;
    private final GenreService genreService;

    @Autowired
    public AdminSongCompilationRestController(SongCompilationService songCompilationService,
                                              FileUploadService fileUploadService, GenreService genreService) {
        this.songCompilationService = songCompilationService;
        this.fileUploadService = fileUploadService;
        this.genreService = genreService;
    }

    @GetMapping
    public List<SongCompilation> getAllCompilation() {
        LOGGER.info("GET request '/'");
        List<SongCompilation> compilations = songCompilationService.getAllSongCompilations();
        LOGGER.info("Result has {} lines", compilations.size());
        return compilations;
    }

    @GetMapping("/content/{compilationId}")
    public List<Song> getAllWithGenreByGenreId(@PathVariable Long compilationId) {
        LOGGER.info("GET request '/content/{}'", compilationId);
        List<Song> songs = songCompilationService.getSongCompilationContentById(compilationId);
        LOGGER.info("Result has {} lines", songs.size());
        return songs;
    }

    @GetMapping("/content/available/{compilationId}")
    public List<Song> getAvailableWithGenreByGenreId(@PathVariable Long compilationId) {
        LOGGER.info("GET request '/content/available/{}'", compilationId);
        List<Song> songs = songCompilationService.getAvailableSongsForCompilationById(compilationId);
        LOGGER.info("Result has {} lines", songs.size());
        return songs;
    }

    @PostMapping("/content/add/{compilationId}/{songId}")
    public void addSongToSongCompilation(@PathVariable Long compilationId, @PathVariable Long songId) {
        LOGGER.info("POST request '/content/add/{}/{}'", compilationId, songId);
        songCompilationService.addSongToSongCompilation(compilationId, songId);
        LOGGER.info("SongCompilation ID = {} now contains Song ID = {}", compilationId, songId);
    }

    @PostMapping("/update")
    public void updateCompilation(@ModelAttribute SongCompilationDto songCompilationDto) throws IOException {
        LOGGER.info("POST request '/update' songCompilationDto ID = {}", songCompilationDto.getId());
        SongCompilation compilation = songCompilationService.getSongCompilationById(songCompilationDto.getId());
        LOGGER.info("Song compilation with ID = {} exists", compilation.getId());

        String coverName = uploadCover(songCompilationDto.getCover());
        if (coverName != null) {
            // Удалить текущую обложку, если была
            if (compilation.getCover() != null) {
                fileUploadService.eraseCurrentFile(compilation.getCover());
                LOGGER.info("Old cover deleted");
            }
            compilation.setCover(coverName);
            LOGGER.info("Cover updated for song compilation with ID = {}", compilation.getId());
        }

        compilation.setName(songCompilationDto.getName());
        LOGGER.info("Compilation Editing: name set -> {}", songCompilationDto.getName());
        compilation.setGenre(
                genreService.getByName(songCompilationDto.getGenre()));
        LOGGER.info("Compilation Editing: genre set -> {}", songCompilationDto.getGenre());

        songCompilationService.updateCompilation(compilation);
        LOGGER.info("Song compilation with ID = {} updated successfully", compilation.getId());
    }

    @PostMapping
    public void addCompilation(@ModelAttribute SongCompilationDto songCompilationDto) throws IOException {
        LOGGER.info("POST request '/' to add a new SongCompilation with temporary name -> {}",
                songCompilationDto.getName());

        SongCompilation songCompilation = new SongCompilation();
        songCompilation.setName(songCompilationDto.getName());
        songCompilation.setGenre(genreService.getByName(songCompilationDto.getGenre()));
        String coverName = uploadCover(songCompilationDto.getCover());
        if (coverName != null) {
            songCompilation.setCover(coverName);
        }

        songCompilationService.addSongCompilation(songCompilation);
        LOGGER.info("SongCompilation name = {} added", songCompilation.getName());
    }

    @DeleteMapping("/delete")
    public void deleteCompilation(@RequestBody Long id) throws IOException {
        LOGGER.info("DELETE request '/delete' songCompilationDto ID = {}", id);
        songCompilationService.deleteSongCompilation(songCompilationService.getSongCompilationById(id));
        LOGGER.info("SongCompilation with ID = {} deleted", id);
    }

    @DeleteMapping("/content/remove/{compilationId}/{songId}")
    public void removeSongFromSongCompilation(@PathVariable Long compilationId, @PathVariable Long songId) {
        LOGGER.info("DELETE request '/content/delete/{}/{}'", compilationId, songId);
        songCompilationService.removeSongFromSongCompilation(compilationId, songId);
        LOGGER.info("Song ID = {} removed from SongCompilation ID = {}", songId, compilationId);
    }

    private String uploadCover(MultipartFile file) throws IOException {
        String coverName = null;
        if (file != null && fileUploadService.isImage(file.getOriginalFilename())) {
            coverName = fileUploadService.upload(file);
            LOGGER.info("Cover uploaded. Cover name: {}", coverName);
        }
        return coverName;
    }
}
