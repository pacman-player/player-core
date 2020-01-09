package spring.app.controller.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping
    public String getUserPage() {
        return "user/statistics";
    }

    @GetMapping("/top")
    public String getUserTop() {
        return "user/top";
    }

    @GetMapping("/statistics")
    public String getUserStatistics() {
        return "user/statistics";
    }

    @GetMapping("/promo")
    public String getUserPromo() {
        return "user/promo";
    }

    @GetMapping("/company")
    public String getUserEstablishment() {
        return "user/company";
    }

    @PostMapping("/company")
    public String getAddress(@RequestBody String address, Model model) {
        System.out.println(address);

        model.addAttribute("address", address);

        return "redirect:/user/company";
    }

    @GetMapping("/filter")
    public String getUserFilter() {
        return "user/filter";
    }

    @GetMapping("/compilation")
    public String getUserCompilation() {
        return "user/compilation";
    }

    @GetMapping("/fileUpload")
    public String fileUpload() {
        return "user/fileUpload";
    }
}