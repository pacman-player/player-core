package spring.app.configuration.initializer;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import spring.app.model.*;
import spring.app.service.abstraction.*;

import java.io.File;
import java.io.IOException;
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

    @Autowired
    private AddressService addressService;

    @Value("${music.path}")
    private String musicPath;

    private void init() throws InvalidDataException, IOException, UnsupportedTagException {
        File musicDirectory = new File(musicPath);
        if (!musicDirectory.exists()) {
            musicDirectory.mkdir();
        }
        Role roleAdmin = new Role();
        roleAdmin.setName("ADMIN");
        roleService.addRole(roleAdmin);

        Role roleUser = new Role();
        roleUser.setName("USER");
        roleService.addRole(roleUser);


        Role roleManager = new Role();
        roleManager.setName("ACTUATOR");
        roleService.addRole(roleManager);

        Role roleAnonymous = new Role();
        roleAnonymous.setName("ANONYMOUS");
        roleService.addRole(roleAnonymous);

        Role rolePreuser = new Role();
        rolePreuser.setName("PREUSER");
        roleService.addRole(rolePreuser);

        User admin = new User();
        admin.setLogin("admin");
        admin.setPassword("admin");
        admin.setEmail("admin@gmail.com");
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(roleAdmin);
        adminRoles.add(roleUser);
        adminRoles.add(roleManager);
        admin.setRoles(adminRoles);
        userService.addUser(admin);

        User admin1 = new User();
        admin1.setLogin("admin1");
        admin1.setPassword("admin");
        admin1.setEmail("admin1@gmail.com");
        admin1.setRoles(adminRoles);
        userService.addUser(admin1);

        User user = new User();
        user.setLogin("user");
        user.setPassword("user");
        user.setEmail("user@gmail.com");
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(roleUser);
        user.setRoles(userRoles);
        userService.addUser(user);

        User user2 = new User();
        user2.setLogin("user2");
        user2.setPassword("user2");
        user2.setEmail("user2@gmail.com");
        userRoles.add(roleUser);
        user2.setRoles(userRoles);
        userService.addUser(user2);

        User user3 = new User();
        user3.setLogin("user3");
        user3.setPassword("user3");
        user3.setEmail("user3@gmail.com");
        userRoles.add(roleUser);
        user3.setRoles(userRoles);
        userService.addUser(user3);

        User user4 = new User();
        user4.setLogin("user4");
        user4.setPassword("user4");
        user4.setEmail("user4@gmail.com");
        userRoles.add(roleUser);
        user4.setRoles(userRoles);
        userService.addUser(user4);

        User user5 = new User();
        user5.setLogin("user5");
        user5.setPassword("user5");
        user5.setEmail("user5@gmail.com");
        userRoles.add(roleUser);
        user5.setRoles(userRoles);
        userService.addUser(user5);

        User user6 = new User();
        user6.setLogin("user6");
        user6.setPassword("user6");
        user6.setEmail("user6@gmail.com");
        userRoles.add(roleUser);
        user6.setRoles(userRoles);
        userService.addUser(user6);

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
//
//        Author author = new Author();
//        author.setName("author-1");
        Set<Genre> genres = new HashSet<>();
        genres.add(genre);
//        author.setAuthorGenres(genres);
//        authorService.addAuthor(author);

        OrgType orgType = new OrgType("Restaurant");
        orgType.setGenres(genres);
        orgTypeService.addOrgType(orgType);

////        Song song0 = new Song("Attack on Titan - DOA");
////        song0.setAuthor(author);
////        song0.setGenre(genre);
////        songService.addSong(song0);
////
////        Song song1 = new Song("Attack on TItan - YouSeeBIGGIRL");
////        song1.setAuthor(author);
////        song1.setGenre(genre1);
////        songService.addSong(song1);
////
////        Song song2 = new Song("Hiiragi Nao - Requiem");
////        song2.setAuthor(author);
////        song2.setGenre(genre2);
////        songService.addSong(song2);
////
////        Song song3 = new Song("Jessie_J_BoB_-_Price_Tag");
////        song3.setAuthor(author);
////        song3.setGenre(genre3);
////        songService.addSong(song3);
////
////        Song song4 = new Song("Kuroko no basuke - Koushu");
////        song4.setAuthor(author);
////        song4.setGenre(genre4);
////        songService.addSong(song4);
////
////        Song song10 = new Song("Lyubje - Davajj za");
////        song10.setAuthor(author);
////        song10.setGenre(genre);
////        songService.addSong(song10);
////
////        Song song11 = new Song("LYUBJE - Pozovi menya tikho po imeni");
////        song11.setAuthor(author);
////        song11.setGenre(genre1);
////        songService.addSong(song11);
////
////        Song song12 = new Song("Lyubovnye istorii - SHkola");
////        song12.setAuthor(author);
////        song12.setGenre(genre2);
////        songService.addSong(song12);
////
////        Song song13 = new Song("Namika - Lieblingsmensch");
////        song13.setAuthor(author);
////        song13.setGenre(genre3);
////        songService.addSong(song13);
////
////        Song song14 = new Song("non-stop - kirgizstanim");
////        song14.setAuthor(author);
////        song14.setGenre(genre4);
////        songService.addSong(song14);
////
////        Song song20 = new Song("Polina Gagarina - Kukushka");
////        song20.setAuthor(author);
////        song20.setGenre(genre4);
////        songService.addSong(song20);
////
////        Song song21 = new Song("Parazit - I AM");
////        song21.setAuthor(author);
////        song21.setGenre(genre1);
////        songService.addSong(song21);
////
////        Song song22 = new Song("Seven Deadly Sins - Perfect Time - Guitar");
////        song22.setAuthor(author);
////        song22.setGenre(genre2);
////        songService.addSong(song22);
////
////        Song song23 = new Song("Your lie in April - Hikaru Nara");
////        song23.setAuthor(author);
////        song23.setGenre(genre3);
////        songService.addSong(song23);
////
////        Song song24 = new Song("YUrijj SHatunov - Belye rozy");
////        song24.setAuthor(author);
////        song24.setGenre(genre4);
////        songService.addSong(song24);
//
////        SongCompilation songCompilation = new SongCompilation();
////        Set<Song> songs = new HashSet<>(Arrays.asList(song0, song10, song20));
////        songCompilation.setName("compilation0");
////        songCompilation.setGenre(genre);
////        songCompilation.setSong(songs);
////        songCompilationService.addSongСompilation(songCompilation);
////
////        SongCompilation songCompilation1 = new SongCompilation();
////        Set<Song> songs1 = new HashSet<>(Arrays.asList(song1, song11, song21));
////        songCompilation1.setName("compilation1");
////        songCompilation1.setGenre(genre1);
////        songCompilation1.setSong(songs1);
////        songCompilationService.addSongСompilation(songCompilation1);
////
////        SongCompilation songCompilation2 = new SongCompilation();
////        Set<Song> songs2 = new HashSet<>(Arrays.asList(song2, song12, song22));
////        songCompilation2.setName("compilation2");
////        songCompilation2.setGenre(genre2);
////        songCompilation2.setSong(songs2);
////        songCompilationService.addSongСompilation(songCompilation2);
////
////        SongCompilation songCompilation3 = new SongCompilation();
////        Set<Song> songs3 = new HashSet<>(Arrays.asList(song3, song13, song23));
////        songCompilation3.setName("compilation3");
////        songCompilation3.setGenre(genre3);
////        songCompilation3.setSong(songs3);
////        songCompilationService.addSongСompilation(songCompilation3);
////
////        SongCompilation songCompilation4 = new SongCompilation();
////        Set<Song> songs4 = new HashSet<>(Arrays.asList(song4, song14, song24));
////        songCompilation4.setName("compilation4");
////        songCompilation4.setGenre(genre4);
////        songCompilation4.setSong(songs4);
////        songCompilationService.addSongСompilation(songCompilation4);
//
//
        PlayList playList = new PlayList();
        playList.setName("All day playlist");
        playListService.addPlayList(playList);

        PlayList playList1 = new PlayList();
        playList1.setName("Morning playlist");
        playListService.addPlayList(playList1);

        PlayList playList2 = new PlayList();
        playList2.setName("Midday playlist");
        playListService.addPlayList(playList2);

        PlayList playList3 = new PlayList();
        playList3.setName("Evening playlist");
        playListService.addPlayList(playList3);

        Company company = new Company("Mr.Bo", LocalTime.of(11, 0), LocalTime.of(23, 0), user, orgType);
        Company company2 = new Company("Mr.Bo2", LocalTime.of(11, 0), LocalTime.of(23, 0), user2, orgType);
        Company company3 = new Company("Mr.Bo3", LocalTime.of(11, 0), LocalTime.of(23, 0), user3, orgType);
        Company company4 = new Company("Mr.Bo4", LocalTime.of(11, 0), LocalTime.of(23, 0), user4, orgType);
        Company company5 = new Company("Mr.Bo5", LocalTime.of(11, 0), LocalTime.of(23, 0), user5, orgType);
        Company company6 = new Company("Mr.Bo6", LocalTime.of(11, 0), LocalTime.of(23, 0), user6, orgType);

        Set<PlayList> allDayPlayLists = new HashSet<>();
        allDayPlayLists.add(playList);

        Set<PlayList> morningPlayLists = new HashSet<>();
        morningPlayLists.add(playList1);

        Set<PlayList> middayPlayLists = new HashSet<>();
        middayPlayLists.add(playList2);

        Set<PlayList> eveningPlayLists = new HashSet<>();
        eveningPlayLists.add(playList3);

        company.setMorningPlayList(morningPlayLists);
        company.setMiddayPlayList(middayPlayLists);
        company.setEveningPlayList(eveningPlayLists);
        Set<Genre> bannedGenres = new HashSet<>();
        bannedGenres.add(genre1);
        company.setBannedGenres(bannedGenres);
        companyService.addCompany(company);

        company2.setMorningPlayList(morningPlayLists);
        company2.setMiddayPlayList(middayPlayLists);
        company2.setEveningPlayList(eveningPlayLists);
        company2.setBannedGenres(bannedGenres);
        companyService.addCompany(company2);

        company3.setMorningPlayList(morningPlayLists);
        company3.setMiddayPlayList(middayPlayLists);
        company3.setEveningPlayList(eveningPlayLists);
        company3.setBannedGenres(bannedGenres);
        companyService.addCompany(company3);

        company4.setMorningPlayList(morningPlayLists);
        company4.setMiddayPlayList(middayPlayLists);
        company4.setEveningPlayList(eveningPlayLists);
        company4.setBannedGenres(bannedGenres);
        companyService.addCompany(company4);

        company5.setMorningPlayList(morningPlayLists);
        company5.setMiddayPlayList(middayPlayLists);
        company5.setEveningPlayList(eveningPlayLists);
        company5.setBannedGenres(bannedGenres);
        companyService.addCompany(company5);

        company6.setMorningPlayList(morningPlayLists);
        company6.setMiddayPlayList(middayPlayLists);
        company6.setEveningPlayList(eveningPlayLists);
        company6.setBannedGenres(bannedGenres);
        companyService.addCompany(company6);

    }
}
