package spring.app.configuration.initializer;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import spring.app.model.*;
import spring.app.service.abstraction.*;
import spring.app.util.Mp3Parser;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

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
    private OrderSongService orderSongService;

    @Autowired
    private SongCompilationService songCompilationService;

    @Autowired
    private AddressService addressService;

    @Value("${music.path}")
    private String musicPath;

    private void init() throws InvalidDataException, IOException, UnsupportedTagException {
        if (new File(musicPath).exists()) {
            FileUtils.cleanDirectory(new File(musicPath));
        }
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

//        Author author = new Author();
//        author.setName("author-1");
        Set<Genre> genres = new HashSet<>();
        genres.add(genre);
//        author.setAuthorGenres(genres);
//        authorService.addAuthor(author);

        OrgType orgType = new OrgType("Restaurant");
        orgType.setGenres(genres);
        orgTypeService.addOrgType(orgType);

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

        //adding mock statistics
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, -90);
        long startDate = cal.getTime().getTime();
        long endDate = new Date().getTime();
        Random random = new Random(System.currentTimeMillis());
        long totalOrders = random.nextInt(3000);
        for (int i = 0; i < totalOrders; i++) {
            orderSongService.addSongOrder(new OrderSong(company, new Timestamp(ThreadLocalRandom.current()
                    .nextLong(startDate, endDate))));
        }

//        new Mp3Parser(songService, authorService, genreService, songCompilationService).apply("music1/");

    }
}
