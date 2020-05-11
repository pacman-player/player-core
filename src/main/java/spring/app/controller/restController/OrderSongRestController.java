package spring.app.controller.restController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import spring.app.dto.SongDto;
import spring.app.model.User;
import spring.app.service.abstraction.CompanyService;
import spring.app.service.abstraction.OrderSongService;
import spring.app.service.abstraction.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
public class OrderSongRestController {
    private final static Logger LOGGER = LoggerFactory.getLogger(OrderSongRestController.class);
    private OrderSongService orderSongService;


    private UserService userService;

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
    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping(value = "/getOrders")
    public List<String> getOrders() {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user = userService.getUserByLogin(user.getUsername());
        Long companyId = user.getCompany().getId();
        List<String> ordersClassified = new ArrayList<>();
        ordersClassified.add("orders-today-total:" + orderSongService.getSongOrdersByCompanyIdAndTimeRange(companyId, "today"));
        ordersClassified.add("orders-yesterday-total:" + orderSongService.getSongOrdersByCompanyIdAndTimeRange(companyId, "yesterday"));
        ordersClassified.add("orders-week-total:" + orderSongService.getSongOrdersByCompanyIdAndPeriod(companyId, 7L));
        ordersClassified.add("orders-month-total:" + orderSongService.getSongOrdersByCompanyIdAndPeriod(companyId, 30L));
        ordersClassified.add("orders-total-total:" + orderSongService.countAll(companyId));
        return ordersClassified;
    }

    @GetMapping(value = "/getSongsInQueue")
    public List<String> getSongsInQueue() {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user = userService.getUserByLogin(user.getUsername());
        Long companyId = user.getCompany().getId();
        List<String> queueList = companyService.getAllSongsInQueueByCompanyId(companyId);
        return queueList;
    }


}
