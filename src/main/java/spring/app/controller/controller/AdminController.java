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

    @GetMapping("/compilations")
    public String getCollections() {
        return "admin/compilations";
    }

    @GetMapping("/genres")
    public String getGenres() {
        return "admin/genres";
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
}


