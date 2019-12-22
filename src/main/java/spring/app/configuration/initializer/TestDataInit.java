package spring.app.configuration.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import spring.app.model.*;
import spring.app.service.abstraction.*;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

public class TestDataInit {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private OrgTypeService orgTypeService;

    @Autowired
    private SongService songService;

    @Autowired
    private PlayListService playListService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private SongQueueService songQueueService;

    @Autowired
    private SongCompilationService songCompilationService;

    private void init() {

        Role roleAdmin = new Role();
        roleAdmin.setName("ADMIN");
        roleService.addRole(roleAdmin);

        Role roleUser = new Role();
        roleUser.setName("USER");
        roleService.addRole(roleUser);

        User admin = new User();
        admin.setLogin("admin");
        admin.setPassword("admin");
        admin.setEmail("admin@gmail.com");
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(roleAdmin);
        adminRoles.add(roleUser);
        admin.setRoles(adminRoles);
        userService.addUser(admin);

        User user = new User();
        user.setLogin("user");
        user.setPassword("user");
        user.setEmail("user@gmail.com");
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(roleUser);
        user.setRoles(userRoles);
        userService.addUser(user);

        Genre genre = new Genre("world");
        genreService.addGenre(genre);

        Genre genre1 = new Genre("hard rock");
        genreService.addGenre(genre1);

        Genre genre2 = new Genre("classic");
        genreService.addGenre(genre2);

        Genre genre3 = new Genre("folk");
        genreService.addGenre(genre3);

        Genre genre4 = new Genre("punk");
        genreService.addGenre(genre4);

        Author author = new Author();
        author.setName("author-1");
        Set<Genre> genres = new HashSet<>();
        genres.add(genre);
        author.setAuthorGenres(genres);
        authorService.addAuthor(author);

        OrgType orgType = new OrgType("Restaurant");
        orgType.setGenres(genres);
        orgTypeService.addOrgType(orgType);

        Song song = new Song("Desert Rose");
        song.setAuthor(author);
        song.setGenre(genre);
        songService.addSong(song);

        SongCompilation songCompilation = new SongCompilation();
        Set<Song> songs = new HashSet<>();
        songs.add(song);
        songCompilation.setName("compilation0");
        songCompilation.setGenre(genre);
        songCompilation.setSong(songs);
        songCompilationService.addSongСompilation(songCompilation);

        SongCompilation songCompilation1 = new SongCompilation();
        Set<Song> songs1 = new HashSet<>();
        songs1.add(song);
        songCompilation1.setName("compilation1");
        songCompilation1.setGenre(genre1);
        songCompilation1.setSong(songs1);
        songCompilationService.addSongСompilation(songCompilation1);

        SongCompilation songCompilation2 = new SongCompilation();
        Set<Song> songs2 = new HashSet<>();
        songs2.add(song);
        songCompilation2.setName("compilation2");
        songCompilation2.setGenre(genre2);
        songCompilation2.setSong(songs2);
        songCompilationService.addSongСompilation(songCompilation2);

        SongCompilation songCompilation3 = new SongCompilation();
        Set<Song> songs3 = new HashSet<>();
        songs3.add(song);
        songCompilation3.setName("compilation3");
        songCompilation3.setGenre(genre3);
        songCompilation3.setSong(songs3);
        songCompilationService.addSongСompilation(songCompilation3);

        SongCompilation songCompilation4 = new SongCompilation();
        Set<Song> songs4 = new HashSet<>();
        songs4.add(song);
        songCompilation4.setName("compilation4");
        songCompilation4.setGenre(genre4);
        songCompilation4.setSong(songs4);
        songCompilationService.addSongСompilation(songCompilation4);


        PlayList playList = new PlayList();
        playList.setName("All day playlist");
        playListService.addPlayList(playList);

        Company company = new Company("Mr.Bo", LocalTime.of(11, 0), LocalTime.of(23, 0), user, orgType);
        Set<PlayList> playLists = new HashSet<>();
        playLists.add(playList);
        company.setMorningPlayList(playLists);
        company.setMiddayPlayList(playLists);
        company.setEveningPlayList(playLists);
        Set<Genre> bannedGenres = new HashSet<>();
        bannedGenres.add(genre1);
        company.setBannedGenres(bannedGenres);
        companyService.addCompany(company);

        SongQueue songQueue = new SongQueue();
        songQueue.setPosition(1L);
        songQueue.setSong(song);
        songQueue.setCompany(company);
        songQueueService.addSongQueue(songQueue);
    }
}
