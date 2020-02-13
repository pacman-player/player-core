package spring.app.controller.restController;

import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.DecoderException;
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
        return telegramService.getSong(songRequest);
    }

    @PostMapping(value = "/approve")
    public SongResponse approve (@RequestBody SongRequest songRequest) throws IOException, BitstreamException, DecoderException {
        return telegramService.approveSong(songRequest);
    }

    @PostMapping(value = "/location")
    public HashMap compareAddress(@RequestBody Address geoAddress){
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
        return listCompanyId;
    }

    @PostMapping(value = "/all_company")
    public List approve () {
        return companyService.getAllCompanies();
    }


    @PostMapping("/addSongToQueue")
    public void addSongToQueue(HttpEntity httpEntity) {
        HttpHeaders headers = httpEntity.getHeaders();
        long songId = Long.parseLong(headers.get("songId").get(0));
        long companyId = Long.parseLong(headers.get("companyId").get(0));
        Song songById = songService.getSongById(songId);
        Company companyById = companyService.getById(companyId);
        SongQueue songQueue = songQueueService.getSongQueueBySongAndCompany(songById, companyById);
        long lastSongQueuesPosition = songQueueService.getLastSongQueuesNumberFromCompany(companyById);
        if (songQueue == null) {
            songQueue = new SongQueue();
            songQueue.setSong(songById);
            songQueue.setCompany(companyById);
            songQueue.setPosition(lastSongQueuesPosition + 1L);
            songQueueService.addSongQueue(songQueue);
            orderSongService.addSongOrder(new OrderSong(companyById, new Timestamp(System.currentTimeMillis())));
            songQueue = songQueueService.getSongQueueBySongAndCompany(songById, companyById);
            companyById.getSongQueues().add(songQueue);
            companyService.updateCompany(companyById);
        } else {
            songQueue.setPosition(lastSongQueuesPosition + 1L);
            songQueueService.updateSongQueue(songQueue);
            orderSongService.addSongOrder(new OrderSong(companyById, new Timestamp(System.currentTimeMillis())));
        }
    }
}
