package spring.app.controller.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    @GetMapping("/users")
    public String getUsers() {
        return "admin/users";
    }

    @GetMapping("/companies")
    public String getCompanies() {
        return "admin/companies";
    }

    @GetMapping("/establishments")
    public String getEstablishments() {
        return "admin/establishments";
    }

    @GetMapping("/roles")
    public String getRoles() {
        return "admin/roles";
    }

    @GetMapping("/compilations")
    public String getCollections() {
        return "admin/compilations";
    }

    @GetMapping("/genres")
    public String getGenres() {
        return "admin/genres";
    }

    @GetMapping("/tags")
    public String getTags() {
        return "admin/tags";
    }

    @GetMapping("/performers")
    public String getPerformers() {
        return "admin/performers";
    }

    @GetMapping("/songs")
    public String getSongs() {
        return "admin/songs";
    }

    @GetMapping("/report")
    public String getVkBot() {
        return "admin/report";
    }

    @GetMapping("/notifications")
    public String getNotifications() {
        return "admin/notifications";
    }

    @GetMapping("/message")
    public String getMessage() {
        return "admin/messages";
    }

    @GetMapping("/songStatistic")
    public String getStatistic() {
        return "admin/songStatistic";
    }
}


