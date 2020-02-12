package spring.app.controller.restController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import spring.app.model.Company;
import spring.app.model.Song;
import spring.app.model.User;
import spring.app.service.abstraction.CompanyService;
import spring.app.service.abstraction.SongService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/music")
public class SongRestController {
    private final static Logger LOGGER = LoggerFactory.getLogger("SongRestController");
    private CompanyService companyService;
    private SongService songService;

    public SongRestController(CompanyService companyService,
                              SongService songService) {
        this.companyService = companyService;
        this.songService = songService;
    }

    @PostMapping("songsBan")
    public void addSongInBan(@AuthenticationPrincipal User user,
                             @RequestBody long songId) {

        Company company = companyService.getById(user.getCompany().getId());
        company.addBannedSong(songService.getById(songId));

        companyService.updateCompany(company);
        user.setCompany(company);
        LOGGER.info("POST request 'songsBan' by User = {} with SongId = {}", user, songId);
    }

    @PostMapping("songsUnBan")
    public void songUnBan(@AuthenticationPrincipal User user,
                          @RequestBody long songId) {
        Company company = user.getCompany();
        company.getBannedSong().removeIf(song -> song.getId().equals(songId));
        companyService.updateCompany(company);

        user.setCompany(company);
        LOGGER.info("POST request 'songsUnBan' by User = {} with SongId = {}", user, songId);
    }

    @GetMapping("allSongsByName/{name}")
    public List<Song> searchByNameInSongs(@PathVariable String name,
                                          @AuthenticationPrincipal User user) {
        List<Song> songs = songService.findSongsByNameContaining(name);
        Company usersCompany = user.getCompany();
        usersCompany = companyService.setBannedEntity(usersCompany);

        companyService.checkAndMarkAllBlockedByTheCompany(usersCompany, songs);
        LOGGER.info("GET request 'allSongsByName/{}' by User = {}", name, user);
        return songs;
    }

}
