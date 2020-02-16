package spring.app.controller.restController;

import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.DecoderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.app.dto.CompanyDto;
import spring.app.dto.SongRequest;
import spring.app.dto.SongResponse;
import spring.app.model.*;
import spring.app.service.abstraction.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/api/tlg")
public class TelegramRestController {
    private final static Logger LOGGER = LoggerFactory.getLogger(TelegramRestController.class);
    @Autowired
    private OrderSongService orderSongService;

    private final TelegramService telegramService;
    private SongService songService;
    private CompanyService companyService;
    private SongQueueService songQueueService;
    private AddressService addressService;

    @Autowired
    public TelegramRestController(TelegramService telegramService, SongService songService, CompanyService companyService, SongQueueService songQueueService, AddressService addressService) {
        this.telegramService = telegramService;
        this.songService = songService;
        this.companyService = companyService;
        this.songQueueService = songQueueService;
        this.addressService = addressService;
    }

    @PostMapping(value = "/song")
    public SongResponse searchRequestedSong (@RequestBody SongRequest songRequest) throws IOException {
        LOGGER.info("POST request '/song'");
        try {
            LOGGER.info("Requested song Name = {} and Author = {}",
                    songRequest.getSongName(),
                    songRequest.getAuthorName());
            SongResponse songResponse = telegramService.getSong(songRequest);
            LOGGER.info("Got Song response successfully");
            return songResponse;
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
    }

    @PostMapping(value = "/approve")
    public SongResponse approve (@RequestBody SongRequest songRequest) throws IOException, BitstreamException, DecoderException {
        LOGGER.info("POST request '/approve'");
        try {
            LOGGER.info("Requested song Name = {} and Author = {}",
                    songRequest.getSongName(),
                    songRequest.getAuthorName());
            SongResponse songResponse = telegramService.approveSong(songRequest);
            LOGGER.info("Approved Song successfully");
            return songResponse;
        } catch (BitstreamException | DecoderException | IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
    }

    @PostMapping(value = "/location")
    public HashMap compareAddress(@RequestBody Address geoAddress){
        LOGGER.info("POST request '/location' with Address = {}", geoAddress);
        List list = addressService.checkAddress(geoAddress);
        HashMap listCompanyId = new HashMap();
        while (!list.isEmpty()){
            int i = 0;
            Long ii = 1L;
            Address address = (Address) list.get(i);
            Company company = companyService.getCompanyByAddressId(address.getId());
            CompanyDto companyDto = new CompanyDto(ii, company.getId(), company.getName());
            listCompanyId.put(ii, companyDto.getCompanyId());
            listCompanyId.put(ii+1, companyDto.getName());
            list.remove(0);
            i++;
        }
        LOGGER.info("Found {} companies", listCompanyId.size());
        return listCompanyId;
    }

    @PostMapping(value = "/all_company")
    public List approve() {
        LOGGER.info("POST request '/all_company'");
        List<Company> list = companyService.getAllCompanies();
        LOGGER.info("Result has {} lines", list.size());
        return list;
    }


    @PostMapping("/addSongToQueue")
    public void addSongToQueue(HttpEntity httpEntity) {
        LOGGER.info("POST request '/addSongToQueue'");
        HttpHeaders headers = httpEntity.getHeaders();
        long songId = Long.parseLong(headers.get("songId").get(0));
        long companyId = Long.parseLong(headers.get("companyId").get(0));
        LOGGER.info("Provided songId = {} and companyId = {}", songId, companyId);
        Song songById = songService.getSongById(songId);
        Company companyById = companyService.getById(companyId);
        SongQueue songQueue = songQueueService.getSongQueueBySongAndCompany(songById, companyById);
        long lastSongQueuesPosition = songQueueService.getLastSongQueuesNumberFromCompany(companyById);
        if (songQueue == null) {
            LOGGER.info("Song queue is empty. Adding song to queue...");
            songQueue = new SongQueue();
            songQueue.setSong(songById);
            songQueue.setCompany(companyById);
            songQueue.setPosition(lastSongQueuesPosition + 1L);
            songQueueService.addSongQueue(songQueue);
            orderSongService.addSongOrder(new OrderSong(companyById, new Timestamp(System.currentTimeMillis())));
            songQueue = songQueueService.getSongQueueBySongAndCompany(songById, companyById);
            companyById.getSongQueues().add(songQueue);
            companyService.updateCompany(companyById);
            LOGGER.info("Success!");
        } else {
            LOGGER.info("Adding song to existing queue...");
            songQueue.setPosition(lastSongQueuesPosition + 1L);
            songQueueService.updateSongQueue(songQueue);
            orderSongService.addSongOrder(new OrderSong(companyById, new Timestamp(System.currentTimeMillis())));
            LOGGER.info("Success!");
        }
    }
}
