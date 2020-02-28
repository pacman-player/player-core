package spring.app.configuration.initializer;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import spring.app.model.*;
import spring.app.service.abstraction.*;
import spring.app.service.impl.musicSearcher.MusicSearchServiceImpl;
import spring.app.util.Mp3Parser;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class TestDataInit {
    private final static Logger LOGGER = LoggerFactory.getLogger(TestDataInit.class);

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

    @Autowired
    private MusicSearchService musicSearchService;

    @Autowired
    private RegistrationStepService registrationStepService;

    @Value("${music.path}")
    private String musicPath;

    @Value("${music.initPath}")
    private String  musicInitPath;

    private void init() throws InvalidDataException, IOException, UnsupportedTagException {

        // Создаем роли
        Role roleAdmin = new Role("ADMIN");
        roleService.addRole(roleAdmin);

        Role roleUser = new Role("USER");
        roleService.addRole(roleUser);

        Role roleManager = new Role("ACTUATOR");
        roleService.addRole(roleManager);

        Role roleAnonymous = new Role("ANONYMOUS");
        roleService.addRole(roleAnonymous);

        // содаем и добавляем в БД регистрационные шаги для пользователей
        RegistrationStep rs1 = new RegistrationStep();
        rs1.setName("registration-step-user");
        registrationStepService.save(rs1);

        RegistrationStep rs2 = new RegistrationStep();
        rs2.setName("registration-step-company");
        registrationStepService.save(rs2);

        RegistrationStep rs3 = new RegistrationStep();
        rs3.setName("registration-step-address");
        registrationStepService.save(rs3);

        RegistrationStep rsFinal = new RegistrationStep();
        rsFinal.setName("registration-step-end");
        registrationStepService.save(rsFinal);

        // тестовые аккаунты: админ 1
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

        // тестовые аккаунты: админ 2
        User admin1 = new User();
        admin1.setLogin("admin1");
        admin1.setPassword("admin");
        admin1.setEmail("admin1@gmail.com");
        admin1.setRoles(adminRoles);
        userService.addUser(admin1);

        // тестовые аккаунты: юзер 1
        User user = new User();
        user.setLogin("user");
        user.setPassword("user");
        user.setEmail("user@gmail.com");
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(roleUser);
        user.setRoles(userRoles);
        userService.addUser(user);

        // тестовые аккаунты: юзер 2
        User user2 = new User();
        user2.setLogin("user2");
        user2.setPassword("user2");
        user2.setEmail("user2@gmail.com");
        Set<Role> user2Roles = new HashSet<>();
        user2Roles.add(roleUser);
        user2.setRoles(user2Roles);
        userService.addUser(user2);

        // присваиваем нашим юзерам регистрационные шаги (тут полной регистрации
        user = userService.getUserByLoginWithRegStepsCompany("user");
        user2 = userService.getUserByLoginWithRegStepsCompany("user2");
        RegistrationStep registrationStep = registrationStepService.getRegStepById(1L);
        user.addRegStep(registrationStep);
        user2.addRegStep(registrationStep);
        registrationStep = registrationStepService.getRegStepById(2L);
        user.addRegStep(registrationStep);
        user2.addRegStep(registrationStep);
        registrationStep = registrationStepService.getRegStepById(3L);
        user.addRegStep(registrationStep);
        user2.addRegStep(registrationStep);
        userService.updateUser(user);
        userService.updateUser(user2);

        // adding MP3 files  from /music1/ to /music
        LOGGER.info("===== Ready to load music files! =====");
        try {
            File musicInitDirectory = new File(musicInitPath);
            File musicDirectory = new File(musicPath);
            if (musicDirectory.exists()
                    && (musicDirectory.length() != musicInitDirectory.length())) {
                //если каталог присутствует, но количество файлов не совпадает, удаляем каталог и копируем все заново
                FileUtils.deleteDirectory(musicDirectory);
            }
            if (!musicDirectory.exists()){
                LOGGER.info("Looks like '{}' doesn't exist or is altered, gonna parse some MP3 files from '{}'", musicPath, musicInitPath);
                musicDirectory.mkdir();

                new Mp3Parser(songService, authorService, genreService, songCompilationService, musicSearchService)
                        .apply(musicInitPath);
            }
        } catch (Exception e) {
            LOGGER.error("We have issues reading or writing music files during init. Please check if init data is accessible and '/music' directory is writable", e);
            throw e;
        }

        // создаем жанры
        Genre genre1 = new Genre("поп");
        genreService.addGenre(genre1);
        Genre genre2 = new Genre("рок");
        genreService.addGenre(genre2);
        Genre genre3 = new Genre("альтернатива");
        genreService.addGenre(genre3);
        Genre genre4 = new Genre("инди");
        genreService.addGenre(genre4);
        Genre genre5 = new Genre("металл");
        genreService.addGenre(genre5);
        Genre genre6 = new Genre("хардкор");
        genreService.addGenre(genre6);
        Genre genre7 = new Genre("неизвестно");
        genreService.addGenre(genre7);
        Genre genre8 = new Genre("джаз");
        genreService.addGenre(genre8);
        Genre genre9 = new Genre("блюз");
        genreService.addGenre(genre9);
        Genre genre10 = new Genre("американская");
        genreService.addGenre(genre10);
        Genre genre11 = new Genre("пост-панк");
        genreService.addGenre(genre11);
        Genre genre12 = new Genre("фолк");
        genreService.addGenre(genre12);

        // создаем исполнителей
        Author author1 = new Author("OneRepublic, Logic");
        authorService.addAuthor(author1);
        Author author2 = new Author("The Alarm");
        authorService.addAuthor(author2);
        Author author3 = new Author("BLACKPINK");
        authorService.addAuthor(author3);
        Author author4 = new Author("Billie Eilish, Khalid");
        authorService.addAuthor(author4);
        Author author5 = new Author("Katy Perry");
        authorService.addAuthor(author5);
        Author author6 = new Author("Telekinesis");
        authorService.addAuthor(author6);
        Author author7 = new Author("New Order");
        authorService.addAuthor(author7);
        Author author8 = new Author("Parade of Lights");
        authorService.addAuthor(author8);
        Author author9 = new Author("Ed Sheeran");
        authorService.addAuthor(author9);
        Author author10 = new Author("Yungblud");
        authorService.addAuthor(author10);
        Author author11 = new Author("Echo & the Bunnymen");
        authorService.addAuthor(author11);
//        Author author12 = new Author("Yungblud, Charlottle Lawrer");
//        authorService.addAuthor(author12);
        Author author13 = new Author("Tom Walker");
        authorService.addAuthor(author13);

        // создаем и заносим песни. Порядок важен для корректности создания id
        songService.addSong(new Song("Start Again", author1, genre1));
        songService.addSong(new Song("Strength", author2, genre2));
        songService.addSong(new Song("Really", author3, genre3));
        songService.addSong(new Song("lovely", author4, genre1));
        songService.addSong(new Song("Into Me You See", author5, genre5));
        songService.addSong(new Song("Falling (In Dreams)", author6, genre6));
        songService.addSong(new Song("Love Vigilantes", author7, genre7));
        songService.addSong(new Song("Tangled Up", author8, genre8));
        songService.addSong(new Song("Small Bump (Live From Wembley Stadium)", author9, genre9));
        songService.addSong(new Song("Tin Pan Boy", author10, genre10));
        songService.addSong(new Song("The Killing Moon", author11, genre11));
        songService.addSong(new Song("Falling Skies", author10, genre12));
        songService.addSong(new Song("My Way", author13, genre1));

        // создаем ноборы для вставки в mock-компиляции
        Set<Song> songList1 = new HashSet<>();
        Set<Song> songList2 = new HashSet<>();
        Set<Song> songList3 = new HashSet<>();
        Set<Song> songList4 = new HashSet<>();
        List<Song> allSongs = songService.getAllSong();
        // записываем треки по сетам
        for (int i = 0; i < allSongs.size(); i++) {
            Song s = allSongs.get(i);
            if (i < 3) {
                songList2.add(s);
            } else if (i < 6) {
                songList3.add(s);
            }
            else if (i < 9) {
                songList1.add(s);
            } else {
                songList4.add(s);
            }
        }
        // сеты добавляем в подборки
        SongCompilation songCompilation1 = new SongCompilation();
        songCompilation1.setName("My compilation1");
        SongCompilation songCompilation2 = new SongCompilation();
        songCompilation2.setName("My compilation2");
        SongCompilation songCompilation3 = new SongCompilation();
        songCompilation3.setName("My compilation3");
        SongCompilation songCompilation4 = new SongCompilation();
        songCompilation4.setName("My compilation4");
        // присваиваем подборкам жанры
        songCompilation1.setSong(songList1);
        songCompilation1.setGenre(genre2);
        songCompilationService.addSongСompilation(songCompilation1);
        songCompilation2.setSong(songList2);
        songCompilation2.setGenre(genre2);
        songCompilationService.addSongСompilation(songCompilation2);
        songCompilation3.setSong(songList3);
        songCompilation3.setGenre(genre1);
        songCompilationService.addSongСompilation(songCompilation3);
        songCompilation4.setSong(songList4);
        songCompilation4.setGenre(genre3);
        songCompilationService.addSongСompilation(songCompilation4);

        // создаем набор из жанров для вставки в Тип организации
        Set<Genre> genres1 = new HashSet<>();
        Set<Genre> genres2 = new HashSet<>();
        genres1.add(genre1);
        genres1.add(genre2);
        genres2.add(genre3);
        genres2.add(genre4);
        // создаем типы организаций
        OrgType orgType1 = new OrgType("Кальян-бар");
        OrgType orgType2 = new OrgType("Ресторан");
        orgType1.setGenres(genres1);
        orgType2.setGenres(genres2);
        orgTypeService.addOrgType(orgType1);
        orgTypeService.addOrgType(orgType2);

        // создаем компании для наших пользователей
        Company company1 = new Company("Pacman", LocalTime.of(12, 0), LocalTime.of(6, 0), user, orgType1);
        Company company2 = new Company("Обломов", LocalTime.of(10, 0), LocalTime.of(23, 0), user2, orgType2);

        // создаем пустые плейлисты, которые нужны для возможности добавлять
        // и воспроизводить в них музыку (подборки)
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

        PlayList playList4 = new PlayList();
        playList4.setName("All day playlist");
        playListService.addPlayList(playList4);

        PlayList playList5 = new PlayList();
        playList5.setName("Morning playlist");
        playListService.addPlayList(playList5);

        PlayList playList6 = new PlayList();
        playList6.setName("Midday playlist");
        playListService.addPlayList(playList6);

        PlayList playList7 = new PlayList();
        playList7.setName("Evening playlist");
        playListService.addPlayList(playList7);
        // доьбавляем пустые плейлисты в утренние, дневные и вечерние плейлисты
        Set<PlayList> allDayPlayLists = new HashSet<>();
        allDayPlayLists.add(playList);

        Set<PlayList> morningPlayLists = new HashSet<>();
        morningPlayLists.add(playList1);

        Set<PlayList> middayPlayLists = new HashSet<>();
        middayPlayLists.add(playList2);

        Set<PlayList> eveningPlayLists = new HashSet<>();
        eveningPlayLists.add(playList3);

        Set<PlayList> allDayPlayLists2 = new HashSet<>();
        allDayPlayLists2.add(playList4);

        Set<PlayList> morningPlayLists2 = new HashSet<>();
        morningPlayLists2.add(playList5);

        Set<PlayList> middayPlayLists2 = new HashSet<>();
        middayPlayLists2.add(playList6);

        Set<PlayList> eveningPlayLists2 = new HashSet<>();
        eveningPlayLists2.add(playList7);
        // записываем их в наши компании. У каждой компании свои уникальные плейлисты
        company1.setMorningPlayList(morningPlayLists);
        company1.setMiddayPlayList(middayPlayLists);
        company1.setEveningPlayList(eveningPlayLists);
        company2.setMorningPlayList(morningPlayLists2);
        company2.setMiddayPlayList(middayPlayLists2);
        company2.setEveningPlayList(eveningPlayLists2);

        // записываем забаненные жанры для конкретного заведения
        Set<Genre> bannedGenres = new HashSet<>();
        bannedGenres.add(genre7);
        company1.setBannedGenres(bannedGenres);
        company2.setBannedGenres(bannedGenres);
        // добавляем компании в БД
        companyService.addCompany(company1);
        companyService.addCompany(company2);
        // сохраняем адреса и записываем их в компании
        Address address1 = new Address("Россия", "Санкт-Петербург", "Вознесенский пр.", "38",0,0);
        Address address2 = new Address("Россия", "Москва", "1-й Монетчиковский пер.", "5",0,0);
        addressService.addAddress(address1);
        addressService.addAddress(address2);
        company1.setAddress(address1);
        company2.setAddress(address2);
        companyService.updateCompany(company1);
        companyService.updateCompany(company2);


        //adding mock statistics
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, -90);
        long startDate = cal.getTime().getTime();
        long endDate = new Date().getTime();
        Random random = new Random(System.currentTimeMillis());
        long totalOrders = random.nextInt(3000);
        for (int i = 0; i < totalOrders; i++) {
            orderSongService.addSongOrder(new OrderSong(company1, new Timestamp(ThreadLocalRandom.current()
                    .nextLong(startDate, endDate))));
        }

    }
}
