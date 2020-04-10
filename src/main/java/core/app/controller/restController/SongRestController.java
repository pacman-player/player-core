package core.app.controller.restController;

import core.app.model.Company;
import core.app.model.Song;
import core.app.model.User;
import core.app.service.abstraction.CompanyService;
import core.app.service.abstraction.SongService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/music")
public class SongRestController {
    private final static Logger LOGGER = LoggerFactory.getLogger(SongRestController.class);
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
        LOGGER.info("POST request 'songsBan' by User = {} with SongId = {}", user, songId);

        Company company = companyService.getById(user.getCompany().getId());
        company.addBannedSong(songService.getById(songId));

        companyService.updateCompany(company);
        user.setCompany(company);
        LOGGER.info("Song was added to ban for the Company = {}", company.getName());
    }

    @PostMapping("songsUnBan")
    public void songUnBan(@AuthenticationPrincipal User user,
                          @RequestBody long songId) {
        LOGGER.info("POST request 'songsUnBan' by User = {} with SongId = {}", user, songId);
        Company company = user.getCompany();
        company.getBannedSong().removeIf(song -> song.getId().equals(songId));
        companyService.updateCompany(company);

        user.setCompany(company);
        LOGGER.info("Song was removed from ban for the Company = {}", company.getName());
    }

    @GetMapping("allSongsByName/{name}")
    public List<Song> searchByNameInSongs(@PathVariable String name,
                                          @AuthenticationPrincipal User user) {
        LOGGER.info("GET request 'allSongsByName/{}' by User = {}", name, user);
        List<Song> songs = songService.findSongsByNameContaining(name);
        Company usersCompany = user.getCompany();
        usersCompany = companyService.setBannedEntity(usersCompany);

        companyService.checkAndMarkAllBlockedByTheCompany(usersCompany, songs);
        LOGGER.info("Song list ({} song(s)) was added to ban for the Company = {}",
                songs.size(),
                usersCompany.getName());
        return songs;
    }

}
