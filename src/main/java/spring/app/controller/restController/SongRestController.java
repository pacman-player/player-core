package spring.app.controller.restController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import spring.app.dto.SongDto;
import spring.app.dto.SongDtoTop;
import spring.app.model.Company;
import spring.app.model.Song;
import spring.app.model.User;
import spring.app.service.abstraction.CompanyService;
import spring.app.service.abstraction.SongService;

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

    @GetMapping("allSongs")
    public List<SongDto> getAllSongs() {
        return songService.getAllSongsDto();
    }

    @GetMapping("allApprovedSongs")
    public List<Song> getAllApprovedSongs(@AuthenticationPrincipal User user) {
        LOGGER.info("GET request 'allApprovedSongs'");
        List<Song> list = songService.getAllApprovedSongs();

        Company company = user.getCompany();
        company = companyService.setBannedEntity(company);
        companyService.checkAndMarkAllBlockedByTheCompany(
                company,
                list);

        LOGGER.info("Result has {} lines", list.size());
        return list;
    }

    @GetMapping("approvedSongsPage")
    public List<Song> getApprovedSongsPage(@AuthenticationPrincipal User user,
                                               @RequestParam(defaultValue = "1") Integer pageNumber,
                                               @RequestParam(defaultValue = "5") Integer pageSize) {
        LOGGER.info("GET request 'approvedSongsPage'");
        List<Song> songsPage = songService.getApprovedSongsPage(pageNumber, pageSize);

        Company company = user.getCompany();
        company = companyService.setBannedEntity(company);
        companyService.checkAndMarkAllBlockedByTheCompany(
                company,
                songsPage);

        LOGGER.info("Result has {} lines", songsPage.size());
        return songsPage;
    }

    @GetMapping("lastApprovedSongsPageNumber")
    public Integer getLastApprovedSongsPageNumber(@RequestParam(defaultValue = "5") Integer pageSize) {
        return songService.getLastApprovedSongsPageNumber(pageSize);
    }

    @GetMapping("allSongsByName/{name}")
    public List<Song> searchByNameInSongs(@PathVariable String name,
                                          @AuthenticationPrincipal User user) {
        LOGGER.info("GET request 'allSongsByName/{}' by User = {}", name, user);
        List<Song> songs = songService.findByNameContaining(name);
        Company usersCompany = user.getCompany();
        usersCompany = companyService.setBannedEntity(usersCompany);

        companyService.checkAndMarkAllBlockedByTheCompany(usersCompany, songs);
        LOGGER.info("Song list ({} song(s)) was added to ban for the Company = {}",
                songs.size(),
                usersCompany.getName());
        return songs;
    }

    @PostMapping("songsBan")
    public void addSongInBan(@AuthenticationPrincipal User user,
                             @RequestBody long songId) {
        LOGGER.info("POST request 'songsBan' by User = {} with SongId = {}", user, songId);

        Company company = companyService.getById(user.getCompany().getId());
        company.addBannedSong(songService.getById(songId));

        companyService.update(company);
        user.setCompany(company);
        LOGGER.info("Song was added to ban for the Company = {}", company.getName());
    }

    @PostMapping("songsUnBan")
    public void songUnBan(@AuthenticationPrincipal User user,
                          @RequestBody long songId) {
        LOGGER.info("POST request 'songsUnBan' by User = {} with SongId = {}", user, songId);
        Company company = user.getCompany();
        company.getBannedSong().removeIf(song -> song.getId().equals(songId));
        companyService.update(company);

        user.setCompany(company);
        LOGGER.info("Song was removed from ban for the Company = {}", company.getName());
    }
    @PostMapping("getTopSongs/{numbOfList}")
    public List<SongDtoTop> getTopSongs(@PathVariable int numbOfList) {
        LOGGER.info("POST request 'getTopSongs'  numbOfList = {}", numbOfList);
        List<SongDtoTop> songDtos=songService.getTopSongsByNumberOfList(numbOfList);
        return songDtos;
    }
    @PostMapping("getGragicSongs/{numbOfList}/{idSong}")
    public int [][] getGragicSongs(@PathVariable int numbOfList,@PathVariable Long idSong) {
        LOGGER.info("POST request 'getGragicSongs'  numbOfList = {},  idSong = {}  ", numbOfList,idSong);
      //  List<SongDtoTop> songDtos=songService.getTopSongsByNumberOfList(numbOfList);
        SongDtoTop songDtoTop=songService.getSongDtoTopWithPoint(numbOfList,idSong);
        return songDtoTop.getPoint();
    }

}
