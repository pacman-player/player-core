package spring.app.controller.restController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import spring.app.model.SongStatistic;
import spring.app.service.abstraction.SongService;
import spring.app.service.abstraction.SongStatisticService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/statistic/")
public class SongStatisticRestController {
    private final static Logger LOGGER = LoggerFactory.getLogger(SongStatisticRestController.class);
    private final SongService songService;
    private final SongStatisticService songStatisticService;
    private final short STATISTIC_LIMIT = 10;

    public SongStatisticRestController(SongService songService, SongStatisticService songStatisticService) {
        this.songService = songService;
        this.songStatisticService = songStatisticService;
    }

    @GetMapping("/getData")
    public List<SongStatistic> getAllSongStatistic(@RequestParam String period) {
        LOGGER.info("GET request '/statistic/getData'");
        return songStatisticService.getSortedTopList(STATISTIC_LIMIT, period);
    }
}
