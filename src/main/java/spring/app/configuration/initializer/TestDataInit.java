package spring.app.configuration.initializer;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import spring.app.configuration.DownloadMusicServiceConfigurer;
import spring.app.configuration.DownloadMusicServiceConfigurerMBean;
import spring.app.configuration.DownloadMusicServiceFactory;
import spring.app.model.*;
import spring.app.service.abstraction.*;
import spring.app.util.Mp3Parser;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.io.File;
import java.lang.management.ManagementFactory;
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
    private DataUpdateService dataUpdateService;

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
    private DownloadMusicServiceFactory downloadMusicServiceFactory;

    @Autowired
    private RegistrationStepService registrationStepService;

    @Autowired
    private TelegramUserService telegramUserService;

    @Autowired
    private VisitService visitService;

    @Autowired
    private NotificationTemplateService notificationTemplateService;

    @Autowired
    private Mp3Parser mp3Parser;

    @Value("${music.path}")
    private String musicPath;

    @Value("${music.initPath}")
    private String musicInitPath;

    private void init() throws Exception {

        // Создаем роли
        Role roleAdmin = new Role("ADMIN");
        roleService.save(roleAdmin);

        Role roleUser = new Role("USER");
        roleService.save(roleUser);

        Role roleAnonymous = new Role("ANONYMOUS");
        roleService.save(roleAnonymous);

        Role roleBot = new Role("BOT");
        roleService.save(roleBot);

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
        admin.setRoles(adminRoles);
        userService.save(admin);

        // тестовые аккаунты: админ 2
        User admin1 = new User();
        admin1.setLogin("admin1");
        admin1.setPassword("admin");
        admin1.setEmail("admin1@gmail.com");
        admin1.setRoles(adminRoles);
        userService.save(admin1);

        // тестовые аккаунты: юзер 1
        User user = new User();
        user.setLogin("user");
        user.setPassword("user");
        user.setEmail("user@gmail.com");
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(roleUser);
        user.setRoles(userRoles);
        userService.save(user);

        // тестовые аккаунты: юзер 2
        User user2 = new User();
        user2.setLogin("user2");
        user2.setPassword("user2");
        user2.setEmail("user2@gmail.com");
        Set<Role> user2Roles = new HashSet<>();
        user2Roles.add(roleUser);
        user2.setRoles(user2Roles);
        userService.save(user2);

        // тестовые аккаунты: Bot
        User bot = new User();
        bot.setLogin("bot");
        bot.setPassword("bot");
        Set<Role> botRoles = new HashSet<>();
        botRoles.add(roleBot);
        bot.setRoles(botRoles);
        userService.save(bot);

        // присваиваем нашим юзерам регистрационные шаги (тут полной регистрации

        user = userService.getUserByLoginWithRegStepsCompany("user");
        user2 = userService.getUserByLoginWithRegStepsCompany("user2");
        user.addRegStep(rs1);
        user2.addRegStep(rs1);
        user.addRegStep(rs2);
        user2.addRegStep(rs2);
        user.addRegStep(rs3);
        user2.addRegStep(rs3);
        userService.update(user);
        userService.update(user2);

        //создаем дефолтный шаблон для уведомлений
        NotificationTemplate notificationTemplate = new NotificationTemplate();
        notificationTemplate.setName("default");
        notificationTemplate.setTemplate("Был добавлен новый автор {subject}, нужно проверить жанры по {link:genres:ссылке:}");
        notificationTemplateService.create(notificationTemplate);

        // создаем данные для имеющихся песен в /music
        dataUpdateService.updateData("Billie Eilish, Khalid", "Lovely", new String[]{"Поп"});
        dataUpdateService.updateData("BLACKPINK", "Really", new String[]{"Поп"});
        dataUpdateService.updateData("Echo & the Bunnymen", "The Killing Moon", new String[]{"Рок"});
        dataUpdateService.updateData("Ed Sheeran", "Small Bump (Live From Wembley Stadium)", new String[]{"Поп"});
        dataUpdateService.updateData("Katy Perry", "Into Me You See", new String[]{"Поп"});
        dataUpdateService.updateData("New Order", "Love Vigilantes", new String[]{"Рок"});
        dataUpdateService.updateData("OneRepublic, Logic", "Start Again", new String[]{"Поп"});
        dataUpdateService.updateData("Parade of Lights", "Tangled Up", new String[]{"Поп"});
        dataUpdateService.updateData("Telekinesis", "Falling (In Dreams)", new String[]{"Поп"});
        dataUpdateService.updateData("The Alarm", "Strength", new String[]{"Рок"});
        dataUpdateService.updateData("Tom Walker", "My Way", new String[]{"Поп"});
        dataUpdateService.updateData("Yungblud, Charlottle Lawrer", "Falling Skies", new String[]{"Поп"});
        dataUpdateService.updateData("Yungblud", "Tin Pan Boy", new String[]{"Рок"});

        Song song_1 = songService.getByName("Lovely");
        Song song_2 = songService.getByName("Really");
        Song song_3 = songService.getByName("The Killing Moon");
        Song song_4 = songService.getByName("Small Bump (Live From Wembley Stadium)");
        Song song_5 = songService.getByName("Into Me You See");
        Song song_6 = songService.getByName("Love Vigilantes");
        Song song_7 = songService.getByName("Start Again");
        Song song_8 = songService.getByName("Tangled Up");
        Song song_9 = songService.getByName("Falling (In Dreams)");
        Song song_10 = songService.getByName("Strength");
        Song song_11 = songService.getByName("My Way");
        Song song_12 = songService.getByName("Falling Skies");
        Song song_13 = songService.getByName("Tin Pan Boy");

        songService.setTags(song_1, "Billie Eilish Khalid Lovely");
        songService.setTags(song_2, "BLACKPINK Really");
        songService.setTags(song_3, "Echo Bunnymen Killing Moon");
        songService.setTags(song_4, "Ed Sheeran Small Bump");
        songService.setTags(song_5, "Katy Perry Into Me You See");
        songService.setTags(song_6, "New Order Love Vigilantes");
        songService.setTags(song_7, "OneRepublic Logic Start Again");
        songService.setTags(song_8, "Parade of Lights Tangled Up");
        songService.setTags(song_9, "Telekinesis Falling In Dreams");
        songService.setTags(song_10, "The Alarm Strength");
        songService.setTags(song_11, "Tom Walker My Way");
        songService.setTags(song_12, "Yungblud Charlottle Lawrer Falling Skies");
        songService.setTags(song_13, "Yungblud Tin Pan Boy");

        songService.update(song_1);
        songService.update(song_2);
        songService.update(song_3);
        songService.update(song_4);
        songService.update(song_5);
        songService.update(song_6);
        songService.update(song_7);
        songService.update(song_8);
        songService.update(song_9);
        songService.update(song_10);
        songService.update(song_11);
        songService.update(song_12);
        songService.update(song_13);

        // adding MP3 files  from /music1/ to /music
        // adding MP3 files  from /init_song/ to /music
        LOGGER.info("===== Ready to load music files! =====");
        try {
            File musicDirectory = new File(musicPath);
            //если каталог присутствует - удаляем каталог
            if (musicDirectory.exists()) {
                FileUtils.deleteDirectory(musicDirectory);
            }
            if (!musicDirectory.exists()) {
                LOGGER.info("Looks like '{}' doesn't exist or is altered, gonna parse some MP3 files from '{}'", musicPath, musicInitPath);
                musicDirectory.mkdir();

                mp3Parser.apply(musicInitPath);
            }
        } catch (Exception e) {
            LOGGER.error("We have issues reading or writing music files during init. Please check if init data is accessible and '/music' directory is writable", e);
            throw e;
        }

        // здесь ставим флаг approved для проверки что в админке корректно отображается это поле
        songService.getAll().forEach(song -> {
            song.setApproved(true);
            songService.update(song);
        });
        Song song1 = songService.getByName("Start Again");
        Song song3 = songService.getByName("Really");
        song1.setApproved(false);
        song3.setApproved(false);
        songService.update(song1);
        songService.update(song3);
        Author author1 = authorService.getByName("OneRepublic, Logic");
        Author author3 = authorService.getByName("BLACKPINK");
        Author author2 = authorService.getByName("Yungblud");
        Author author4 = authorService.getByName("Yungblud, Charlottle Lawrer");
        Author author5 = authorService.getByName("Tom Walker");
        Author author6 = authorService.getByName("The Alarm");
        author1.setApproved(true);
        author3.setApproved(true);
        author2.setApproved(true);
        author4.setApproved(true);
        author5.setApproved(true);
        author6.setApproved(true);
        authorService.update(author1);
        authorService.update(author3);
        authorService.update(author2);
        authorService.update(author4);
        authorService.update(author5);
        authorService.update(author6);

        // создаем ноборы для вставки в mock-компиляции
        Set<Song> songList1 = new HashSet<>();
        Set<Song> songList2 = new HashSet<>();
        Set<Song> songList3 = new HashSet<>();
        Set<Song> songList4 = new HashSet<>();
        List<Song> allSongs = songService.getAll();
        // записываем треки по сетам
        for (int i = 0; i < allSongs.size(); i++) {
            Song s = allSongs.get(i);
            if (i < 3) {
                songList2.add(s);
            } else if (i < 6) {
                songList3.add(s);
            } else if (i < 9) {
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
        songCompilation1.setGenre(genreService.getByName("Рок"));
        songCompilationService.save(songCompilation1);
        songCompilation2.setSong(songList2);
        songCompilation2.setGenre(genreService.getByName("Поп"));
        songCompilationService.save(songCompilation2);
        songCompilation3.setSong(songList3);
        songCompilation3.setGenre(genreService.getByName("Поп"));
        songCompilationService.save(songCompilation3);
        songCompilation4.setSong(songList4);
        songCompilation4.setGenre(genreService.getByName("Поп"));
        songCompilationService.save(songCompilation4);

        // создаем набор из жанров для вставки в Тип организации
        Set<Genre> genres1 = new HashSet<>();
        Set<Genre> genres2 = new HashSet<>();

        Genre rock = genreService.getByName("Рок");
        rock.setKeywords("рок | метал | альтернатива | панк | хардкор");
        Genre pop = genreService.getByName("Поп");
        pop.setKeywords("поп | популярная | хип | хоп");
        // здесь ставим флаг approved для проверки что в админке корректно отображается это поле
        rock.setApproved(true);
        pop.setApproved(true);
        genreService.update(rock);
        genreService.update(pop);

        //добавляем остальные жанры и ключевые слова к ним
        Genre jazz = new Genre("Джаз", true, "джаз | свинг | диксиленд | бибоп | авангардный | модальный | пост-боп");
        Genre rap = new Genre("Рэп", true, "рэп");
        Genre soul = new Genre("Соул", true, "соул | госпел");
        Genre blues = new Genre("Блюз", true, "блюз");
        Genre reggie = new Genre("Регги/Ска", true, "регги | реггетон | даб | рагга | дэнсхолл");
        Genre folk = new Genre("Фолк", true, "фолк | народная");
        Genre country = new Genre("Кантри", true, "кантри");
        Genre romance = new Genre("Шансон", true, "шансон | авторская");
        Genre dance = new Genre("Танцевальная", true, "электронная | танцевальная | хаус | техно | транс | чип " +
                "| рэйв | индастриал | эмбиент | дабстеп | хардстайл | дип-хаус | даунтемпо | нью-эйдж | олдскул-джангл");
        Genre lounge = new Genre("Лаундж", true, "лаундж | эйдж | медитация");
        Genre classic = new Genre("Классика", true, "классическая | классика | эпохи");
        Genre undefined = new Genre("Неизвестный жанр", false);
        List<Genre> list = Arrays.asList(jazz, rap, soul, blues, reggie, folk,
                country, romance, dance, lounge, classic, undefined);
        genreService.saveBatch(list);
        genres1.add(rock);
        genres1.add(jazz);
        genres2.add(pop);
        genres2.add(rap);
        // создаем типы организаций
        OrgType orgType1 = new OrgType("Кальян-бар");
        OrgType orgType2 = new OrgType("Ресторан");
        // необходимо уточнить логику этого функционала. Дополнительный фильтр по жанрам на основе огранизаций?
        orgType1.setGenres(genres1);
        orgType2.setGenres(genres2);
        orgTypeService.save(orgType1);
        orgTypeService.save(orgType2);

        // создаем компании для наших пользователей
        Company company1 = new Company("Pacman", LocalTime.of(12, 0), LocalTime.of(6, 0), user, 6500L, 60L, orgType1);
        Company company2 = new Company("Обломов", LocalTime.of(10, 0), LocalTime.of(23, 0), user2, 10000L, 120L, orgType2);

        // создаем пустые плейлисты, которые нужны для возможности добавлять
        // и воспроизводить в них музыку (подборки)
        PlayList playList = new PlayList();
        playList.setName("All day playlist");
        playListService.save(playList);

        PlayList playList1 = new PlayList();
        playList1.setName("Morning playlist");
        playListService.save(playList1);

        PlayList playList2 = new PlayList();
        playList2.setName("Midday playlist");
        playListService.save(playList2);

        PlayList playList3 = new PlayList();
        playList3.setName("Evening playlist");
        playListService.save(playList3);

        PlayList playList4 = new PlayList();
        playList4.setName("All day playlist");
        playListService.save(playList4);

        PlayList playList5 = new PlayList();
        playList5.setName("Morning playlist");
        playListService.save(playList5);

        PlayList playList6 = new PlayList();
        playList6.setName("Midday playlist");
        playListService.save(playList6);

        PlayList playList7 = new PlayList();
        playList7.setName("Evening playlist");
        playListService.save(playList7);
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
        bannedGenres.add(genreService.getByName("Поп"));
        company1.setBannedGenres(bannedGenres);
        company2.setBannedGenres(bannedGenres);
        // добавляем компании в БД
        companyService.save(company1);
        companyService.save(company2);
        // сохраняем адреса и записываем их в компании
        Address address1 = new Address("Россия", "Санкт-Петербург", "Вознесенский пр.", "39", 59.923527, 30.307792);
        Address address2 = new Address("Россия", "Москва", "1-й Монетчиковский пер.", "5", 55.732388, 37.628235);
        addressService.save(address1);
        addressService.save(address2);
        company1.setAddress(address1);
        company2.setAddress(address2);
        companyService.update(company1);
        companyService.update(company2);


        //adding mock statistics
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, -90);
        long startDate = cal.getTime().getTime();
        long endDate = new Date().getTime();
        Random random = new Random(System.currentTimeMillis());
//        long totalOrders = random.nextInt(3000);
        long totalOrders = random.nextInt(30);
        for (int i = 0; i < totalOrders; i++) {
            orderSongService.save(new OrderSong(company1, new Timestamp(ThreadLocalRandom.current()
                    .nextLong(startDate, endDate))));
        }

        //Mbean setup here
        DownloadMusicServiceConfigurerMBean serviceConfigurer = new DownloadMusicServiceConfigurer(downloadMusicServiceFactory);
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("MusicServices:type=DownloadMusicServiceConfigurer");
        mBeanServer.registerMBean(serviceConfigurer, name);
    }
}
