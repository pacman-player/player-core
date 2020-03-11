package spring.app.controller.restController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.app.model.Song;
import spring.app.model.User;
import spring.app.service.abstraction.CompanyService;
import spring.app.service.abstraction.OrderSongService;
import spring.app.service.abstraction.SongService;
import spring.app.service.abstraction.UserService;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1")
public class OrderSongRestController {
    private final static Logger LOGGER = LoggerFactory.getLogger(OrderSongRestController.class);
    private OrderSongService orderSongService;

    private UserService userService;
    private SongService songService;
    private CompanyService companyService;

    @Autowired
    public void setOrderSongService(OrderSongService orderSongService) {
        this.orderSongService = orderSongService;
    }
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    public void setSongService(SongService songService) {
        this.songService = songService;
    }
    @Autowired
    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping(value = "/getOrders")
    public List<String> getOrders() {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LOGGER.info("GET request '/getOrders' by User = {}", user);
        user = userService.getUserByLogin(user.getUsername());
        Long companyId = user.getCompany().getId();
        List<String> ordersClassified = new ArrayList<>();
        ordersClassified.add("orders-today-total:" + orderSongService.getSongOrdersByCompanyIdAndTimeRange(companyId, "today"));
        ordersClassified.add("orders-yesterday-total:" + orderSongService.getSongOrdersByCompanyIdAndTimeRange(companyId, "yesterday"));
        ordersClassified.add("orders-week-total:" + orderSongService.getSongOrdersByCompanyIdAndPeriod(companyId, 7L));
        ordersClassified.add("orders-month-total:" + orderSongService.getSongOrdersByCompanyIdAndPeriod(companyId, 30L));
        ordersClassified.add("orders-total-total:" + orderSongService.countAll(companyId));
        LOGGER.info("Result has {} lines", ordersClassified.size());
        return ordersClassified;
    }

    @GetMapping(value = "/getSongsInQueue")
    public List<String> getSongsInQueue() {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LOGGER.info("GET request '/getSongsInQueue' by User = {}", user);
        user = userService.getUserByLogin(user.getUsername());
        Long companyId = user.getCompany().getId();
        List<String> queueList = companyService.getAllSongsInQueueByCompanyId(companyId);
        LOGGER.info("Result has {} lines", queueList.size());
        return queueList;
    }


    @GetMapping(value = "/getTopSong/{period}")
    public Map<String, Long> getTopSongPeriod(@PathVariable Long period) {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user = userService.getUserByLogin(user.getUsername());
        Long companyId = user.getCompany().getId();
        List<Long> listSongId = orderSongService.getListSongIdByCompanyIdAndPeriod(companyId, period);
        Map<Long, Long> songIdAndNumbersRepeat = new HashMap<>();
        for (Long songId : listSongId) {
            if (!songIdAndNumbersRepeat.containsKey(songId)) {
                songIdAndNumbersRepeat.put(songId, 1L);
            } else {
                songIdAndNumbersRepeat.put(songId, songIdAndNumbersRepeat.get(songId) + 1);
            }
        }


             Map<Long, Long> resultLong = songIdAndNumbersRepeat.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                     .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                             (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        Set<Long> setSongId = resultLong.keySet();
        Map<String, Long> result = new LinkedHashMap<>();
        for(Long songId : setSongId) {
            result.put(songService.getSongById(songId).getAuthor().getName() + ", " +
                    songService.getSongById(songId).getName(), resultLong.get(songId));
        }
        return result;
    }

    @GetMapping(value = "/getTopSongTimeRange")
    public List<Long> getTopSongTimeRange() {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user = userService.getUserByLogin(user.getUsername());
        Long companyId = user.getCompany().getId();
        return orderSongService.getListSongIdByCompanyIdAndTimeRange(companyId, "today");
    }
}
