package spring.app.controller.restController;

import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.DecoderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.app.dto.*;
import spring.app.model.*;
import spring.app.service.abstraction.*;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping(value = "/api/tlg")
public class TelegramRestController {
    private final static Logger LOGGER = LoggerFactory.getLogger(TelegramRestController.class);
    private final static ConcurrentHashMap<Long, Instant> reqTimer = new ConcurrentHashMap<>();
    private final OrderSongService orderSongService;
    private final TelegramService telegramService;
    private final SongService songService;
    private final CompanyService companyService;
    private final SongQueueService songQueueService;
    private final AddressService addressService;
    private final TelegramUserService telegramUserService;
    private final VisitService visitService;
    private final SongStatisticService songStatisticService;

    @Autowired
    public TelegramRestController(TelegramService telegramService,
                                  SongService songService,
                                  CompanyService companyService,
                                  SongQueueService songQueueService,
                                  AddressService addressService,
                                  TelegramUserService telegramUserService,
                                  VisitService visitService,
                                  OrderSongService orderSongService,
                                  SongStatisticService songStatisticService) {
        this.telegramService = telegramService;
        this.songService = songService;
        this.companyService = companyService;
        this.songQueueService = songQueueService;
        this.addressService = addressService;
        this.telegramUserService = telegramUserService;
        this.visitService = visitService;
        this.orderSongService = orderSongService;
        this.songStatisticService = songStatisticService;
    }

    /**
     * Получаем 30-секундный отрывок песни по её id. В методе getSong {@link #telegramService} формируем SongResponse.
     *
     * @param songRequest содержит id песни в БД - songId
     * @return SongResponse, содержащий отрезок трека для предпрослушивания и SongId
     * @throws IOException
     * @throws BitstreamException
     * @throws DecoderException
     */
    @PostMapping(value = "/song")
    public SongResponse getRequestedSong(@RequestBody SongRequest songRequest)
            throws IOException, BitstreamException, DecoderException {
        LOGGER.info("POST request '/song'");
        try {
            LOGGER.info("Requested song Id = {}",
                    songRequest.getSongId());
            SongResponse songResponse = telegramService.getSong(songRequest);

            if (songResponse != null) {
                LOGGER.info("Song snippet was created!");
            } else {
                LOGGER.error("Requested song was NOT found! :(");
            }
            return songResponse;
        } catch (BitstreamException | DecoderException | IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Ищем песню на музыкальных сервисах. В методе servicesSearch {@link #telegramService} формируем SongResponse.
     *
     * @param songRequest содержит имя исполнителя, название трека и companyId
     * @return SongResponse, содержащий отрезок трека для предпрослушивания и SongId
     * @throws IOException
     * @throws BitstreamException
     * @throws DecoderException
     */
    @PostMapping(value = "/services_search")
    public ResponseEntity<SongResponse> servicesSearch(@RequestBody SongRequest songRequest)
            throws IOException, BitstreamException, DecoderException {
        LOGGER.info("POST request '/services_search'");
        try {
            String trackName = songRequest.getAuthorName().toUpperCase() + " - " + songRequest.getSongName().toUpperCase();
            int trackCounter = songService.getSongCounterVal(trackName);
            long companyTimer = companyService.getCompanyTimerById(songRequest.getCompanyId());
            Instant currentTime = Instant.now();
            Instant prevTime = reqTimer.get(songRequest.getChatId());
            if (prevTime != null && trackCounter == 0) {
                long difference = companyTimer - (Duration.between(prevTime, currentTime).toMillis() / 1000);
                if (difference > 0) {
                    LOGGER.debug("Следующий заказ для чата {} возможен через {} сек", songRequest.getChatId(), difference);
                    return ResponseEntity.status(228)
                            .header("Timer", String.valueOf(difference))
                            .body(null);
                }
            }
            reqTimer.put(songRequest.getChatId(), currentTime);
            LOGGER.info("Requested song Name = {} and Author = {}",
                    songRequest.getSongName(),
                    songRequest.getAuthorName());
            SongResponse songResponse = telegramService.servicesSearch(songRequest).join();

            if (songResponse != null) {
                LOGGER.info("Song candidate was found by services!");
            } else {
                LOGGER.error("Requested song was NOT found! :(");
            }
            return ResponseEntity.ok(songResponse);
        } catch (BitstreamException | DecoderException | IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Ищем песню в базе данных. В методе databaseSearch {@link #telegramService} формируем SongsListResponse.
     *
     * @param songRequest содержит имя исполнителя, название трека и companyId
     * @return SongsListResponse, содержащий список треков для выбора
     * @throws IOException
     * @throws BitstreamException
     * @throws DecoderException
     */
    @PostMapping(value = "/database_search")
    public SongsListResponse databaseSearch(@RequestBody SongRequest songRequest)
            throws IOException, BitstreamException, DecoderException {
        LOGGER.info("POST request '/database_search'");
        try {
            LOGGER.info("Requested song Name = {} and Author = {}",
                    songRequest.getSongName(),
                    songRequest.getAuthorName());
            SongsListResponse songsListResponse = telegramService.databaseSearch(songRequest);

            if (!songsListResponse.getSongs().isEmpty()) {
                LOGGER.info("Song candidate was found in database!");
            } else {
                LOGGER.error("Requested song was NOT found! :(");
            }
            return songsListResponse;
        } catch (BitstreamException | DecoderException | IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
    }

    @PostMapping(value = "/location")
    public List allCompaniesByAddress(@RequestBody Address geoAddress) {
        LOGGER.info("POST request '/location' with Address = {}", geoAddress);
        List<Address> addresses = addressService.checkAddress(geoAddress);
        List<Company> companies = new ArrayList();

        addresses.forEach(address -> companies.add(companyService.getCompanyByAddressId(address.getId())));
        LOGGER.info("Found {} companies", companies.size());
        return companies;
    }

    @PostMapping(value = "/all_company")
    public List allCompanies() {
        LOGGER.info("POST request '/all_company'");

        List<CompanyDto> list = companyService.getAllCompanies();

        LOGGER.info("Result has {} lines", list.size());
        return list;
    }

    /**
     * Добавляем песню в очередь
     *
     * @param httpEntity
     */
    @PostMapping("/addSongToQueue")
    public void addSongToQueue(HttpEntity httpEntity) {
        LOGGER.info("POST request '/addSongToQueue'");
        // получаем songId и companyId из заголовков Http запроса
        HttpHeaders headers = httpEntity.getHeaders();
        long songId = Long.parseLong(headers.get("songId").get(0));
        long companyId = Long.parseLong(headers.get("companyId").get(0));
        LOGGER.info("Provided songId = {} and companyId = {}", songId, companyId);
        // получаем песню, компанию и очередь компании из базы и обнуляем счетчик сервиса для песни
        songService.resetSongCounter(songId);
        Song songById = songService.getById(songId);
        Company companyById = companyService.getById(companyId);
        SongQueue songQueue = songQueueService.getSongQueueBySongAndCompany(songById, companyById);
        // получаем текущую позицию в очереди
        long lastSongQueuesPosition = songQueueService.getLastSongQueuesNumberFromCompany(companyById);

        if (songQueue == null) {
            LOGGER.info("Song queue is empty. Adding song to queue...");
            // если платная очередь пуста, создаем новую очередь для данной компании
            songQueue = new SongQueue();
            songQueue.setSong(songById);
            songQueue.setCompany(companyById);
            songQueue.setPosition(lastSongQueuesPosition + 1L);
            songQueueService.save(songQueue);
            // вносим новый заказ для статистики
            orderSongService.save(new OrderSong(companyById, new Timestamp(System.currentTimeMillis())));
            //сохранение заказа для статистики прослушиваний
            songStatisticService.saveOrUpdate(new SongStatistic(Date.valueOf(LocalDate.now()), songById.getName(), songById));
            songQueue = songQueueService.getSongQueueBySongAndCompany(songById, companyById);
            companyById.getSongQueues().add(songQueue);
            companyService.update(companyById);
            LOGGER.info("Success!");
        } else {
            LOGGER.info("Adding song to existing queue...");
            // если очередь уже существует, добавляем песню в ее конец
            songQueue.setPosition(lastSongQueuesPosition + 1L);
            songQueueService.update(songQueue);
            // вносим новый заказ для статистики
            orderSongService.save(new OrderSong(companyById, new Timestamp(System.currentTimeMillis())));
            LOGGER.info("Success!");
        }
    }

    @PostMapping("/registerTelegramUserAndVisit")
    public ResponseEntity<?> registerTelegramUserAndVisit(@RequestBody VisitDto visitDto) {
        LOGGER.info("POST request '/registerTelegramUserAndVisit'");
        TelegramUser telegramUser = visitDto.getTelegramUser();
        Company company = companyService.getById(visitDto.getCompanyId());
        telegramUserService.save(telegramUser);
        LOGGER.info(
                "New Telegram user with id = {} was added", telegramUser.getId());
        visitService.addVisit(telegramUser, company);
        LOGGER.info(
                "New visit of Telegram user with id = {} to Company \"{}\" was added",
                telegramUser.getId(), company.getName()
        );
        return ResponseEntity.ok().build();
    }
}