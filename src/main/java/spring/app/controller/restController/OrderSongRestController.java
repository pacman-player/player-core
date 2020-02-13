package spring.app.controller.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.app.model.Song;
import spring.app.model.User;
import spring.app.service.abstraction.CompanyService;
import spring.app.service.abstraction.OrderSongService;
import spring.app.service.abstraction.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1")
public class OrderSongRestController {

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
        return companyService.getAllSongsInQueueByCompanyId(companyId);
    }
}
