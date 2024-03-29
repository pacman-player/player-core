package spring.app.controller.restController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import spring.app.dto.SongCompilationDto;
import spring.app.dto.SongDto;
import spring.app.model.Genre;
import spring.app.model.Response;
import spring.app.model.SongCompilation;
import spring.app.service.abstraction.FileUploadService;
import spring.app.service.abstraction.GenreService;
import spring.app.service.abstraction.SongCompilationService;
import spring.app.service.abstraction.SongService;
import spring.app.util.ResponseBuilder;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin/compilation")
public class AdminSongCompilationRestController {
    private final static Logger LOGGER = LoggerFactory.getLogger(AdminSongCompilationRestController.class);

    private final SongCompilationService songCompilationService;
    private final FileUploadService fileUploadService;
    private final GenreService genreService;
    private final SongService songService;

    @Autowired
    public AdminSongCompilationRestController(SongCompilationService songCompilationService,
                                              FileUploadService fileUploadService, GenreService genreService, SongService songService) {
        this.songCompilationService = songCompilationService;
        this.fileUploadService = fileUploadService;
        this.genreService = genreService;
        this.songService = songService;
    }

    @GetMapping
    public List<SongCompilationDto> getAllCompilation() {
        List<SongCompilationDto> compilations = songCompilationService.getAllSongCompilationDto();
        return compilations;
    }

    @GetMapping("/content/{compilationId}")
    public List<SongDto> getAllWithGenreByGenreId(@PathVariable Long compilationId) {
        List<SongDto> songs = songCompilationService.getSongCompilationContentById(compilationId);
        return songs;
    }

    @GetMapping("/content/available/{compilationId}")
    public List<SongDto> getAvailableWithGenreByGenreIdDto(@PathVariable Long compilationId) {
        List<SongDto> songs = songCompilationService.getAvailableSongsForCompilationByIdDto(compilationId);
        return songs;
    }

//    @PostMapping("/content/add/{compilationId}/{songId}")
//    public void addSongToSongCompilation(@PathVariable Long compilationId, @PathVariable Long songId) {
//        songCompilationService.addSongToSongCompilation(compilationId, songId);
//    }

    @PostMapping(value = "/content/add/{compilationId}/{songId}")
    public Response<String> addSongToSongCompilation(@PathVariable Long compilationId, @PathVariable Long songId) {
        ResponseBuilder<String> responseBuilder = new ResponseBuilder<>();
        SongDto song = songService.getSongDtoById(songId);
        Genre genre = genreService.getByName(song.getGenreName());
        if (!song.getApproved() && !genre.getApproved()){
            return responseBuilder.error("Песня и жанр не прошли модерацию");
        }
        if (!song.getApproved()){
            return responseBuilder.error("Песня не прошла модерацию");
        }
        if (!genre.getApproved()){
            return responseBuilder.error("Жанр не прошел модерацию");
        }
        if (song.getApproved() && genre.getApproved()) {
            songCompilationService.addSongToSongCompilation(compilationId, songId);
        }
        songCompilationService.addSongToSongCompilation(compilationId, songId);
        return responseBuilder.success("Песня добавлена в подборку");
    }

    @PostMapping("/update")
    public ResponseEntity<SongCompilationDto> updateCompilation(@ModelAttribute SongCompilationDto songCompilationDto) throws IOException {
        Long oldId = songCompilationDto.getId();
        SongCompilation oldSongCompilation = songCompilationService.getSongCompilationById(oldId);
        String oldCompilationName = oldSongCompilation.getName();
        String newCompilationName = songCompilationDto.getName();
        if (!oldCompilationName.equals(newCompilationName)) {
            if (songCompilationService.isExist(newCompilationName)) {
                LOGGER.info("SongCompilation is already exists with this", songCompilationDto.getName());
                return ResponseEntity.badRequest().body(songCompilationDto);
            }
        }
        SongCompilation compilation = songCompilationService.getSongCompilationById(songCompilationDto.getId());
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
        songCompilationService.update(compilation);
        LOGGER.info("Song compilation with ID = {} updated successfully", compilation.getId());
        return ResponseEntity.ok(songCompilationDto);
    }

    @PostMapping
    public ResponseEntity<SongCompilationDto> addCompilation(@ModelAttribute SongCompilationDto songCompilationDto) throws IOException {
        LOGGER.info("POST request '/' to add a new SongCompilation with temporary name -> {}",
                songCompilationDto.getName());

        if (songCompilationService.isExist(songCompilationDto.getName())) {
            LOGGER.info("SongCompilation is already exists with this", songCompilationDto.getName());
            return ResponseEntity.badRequest().body(songCompilationDto);
        }
        SongCompilation songCompilation = new SongCompilation();
        songCompilation.setName(songCompilationDto.getName());
        songCompilation.setGenre(genreService.getByName(songCompilationDto.getGenre()));
        String coverName = uploadCover(songCompilationDto.getCover());
        if (coverName != null) {
            songCompilation.setCover(coverName);
        }
        songCompilationService.save(songCompilation);
        LOGGER.info("SongCompilation name = {} added", songCompilation.getName());
        return ResponseEntity.ok(songCompilationDto);
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
