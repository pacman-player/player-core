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

    @Autowired
    private AddressService addressService;

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

        Song song = new Song("Attack on titan - Silence");
        song.setAuthor(author);
        song.setGenre(genre);
        songService.addSong(song);

        Song song1 = new Song("Naruto OST - Hero");
        song1.setAuthor(author);
        song1.setGenre(genre1);
        songService.addSong(song1);

        Song song2 = new Song("Hunter X Hunter - Jokers theme");
        song2.setAuthor(author);
        song2.setGenre(genre2);
        songService.addSong(song2);

        Song song3 = new Song("Death Note - Laits theme");
        song3.setAuthor(author);
        song3.setGenre(genre3);
        songService.addSong(song3);

        Song song4 = new Song("One punch man - Hero");
        song4.setAuthor(author);
        song4.setGenre(genre4);
        songService.addSong(song4);

        SongCompilation songCompilation = new SongCompilation();
        Set<Song> songs = new HashSet<>();
        songs.add(song);
        songCompilation.setName("compilation0");
        songCompilation.setGenre(genre);
        songCompilation.setSong(songs);
        songCompilationService.addSongСompilation(songCompilation);

        SongCompilation songCompilation1 = new SongCompilation();
        Set<Song> songs1 = new HashSet<>();
        songs1.add(song1);
        songCompilation1.setName("compilation1");
        songCompilation1.setGenre(genre1);
        songCompilation1.setSong(songs1);
        songCompilationService.addSongСompilation(songCompilation1);

        SongCompilation songCompilation2 = new SongCompilation();
        Set<Song> songs2 = new HashSet<>();
        songs2.add(song2);
        songCompilation2.setName("compilation2");
        songCompilation2.setGenre(genre2);
        songCompilation2.setSong(songs2);
        songCompilationService.addSongСompilation(songCompilation2);

        SongCompilation songCompilation3 = new SongCompilation();
        Set<Song> songs3 = new HashSet<>();
        songs3.add(song3);
        songCompilation3.setName("compilation3");
        songCompilation3.setGenre(genre3);
        songCompilation3.setSong(songs3);
        songCompilationService.addSongСompilation(songCompilation3);

        SongCompilation songCompilation4 = new SongCompilation();
        Set<Song> songs4 = new HashSet<>();
        songs4.add(song4);
        songCompilation4.setName("compilation4");
        songCompilation4.setGenre(genre4);
        songCompilation4.setSong(songs4);
        songCompilationService.addSongСompilation(songCompilation4);


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

        SongQueue songQueue = new SongQueue();
        songQueue.setPosition(1L);
        songQueue.setSong(song);
        songQueue.setCompany(company);
        songQueueService.addSongQueue(songQueue);

        Address address = new Address();
        address.setLatitude(55.754638);
        address.setLongitude(37.621633);

        address.setCountry("Russia");
        address.setCity("Moscow");
        address.setStreet("Red Square");
        address.setHouse("3");
        addressService.addAddress(address);

        Address address2 = new Address();
        address2.setLatitude(52.022176);
        address2.setLongitude(47.809058);
        addressService.addAddress(address2);

        Address address3 = new Address();
        address3.setLatitude(52.021544);
        address3.setLongitude(47.807657);
        addressService.addAddress(address3);

        Address address4 = new Address();
        address4.setLatitude(52.021855);
        address4.setLongitude(47.810864);
        addressService.addAddress(address4);

        Address address5 = new Address();
        address5.setLatitude(52.02119);
        address5.setLongitude(47.810112);

        address5.setCountry("Russia");
        address5.setCity("Balakovo");
        address5.setStreet("Lenina");
        address5.setHouse("4");
        addressService.addAddress(address5);

        Address address6 = new Address();
        address6.setLatitude(52.021140);
        address6.setLongitude(47.808798);
        addressService.addAddress(address6);

        Address address7 = new Address();
        address7.setLatitude(52.020369);
        address7.setLongitude(47.810774);
        addressService.addAddress(address7);

        company.setAddress(address5);
        companyService.updateCompany(company);
    }
}
