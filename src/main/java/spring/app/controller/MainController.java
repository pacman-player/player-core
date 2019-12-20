package spring.app.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import spring.app.model.Role;
import spring.app.model.User;
import spring.app.service.abstraction.CompanyService;
import spring.app.service.abstraction.GenreService;
import spring.app.service.abstraction.RoleService;
import spring.app.service.abstraction.UserService;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Controller("/test")
public class MainController {

    private final RoleService roleService;
    private final UserService userService;
    private final GenreService genreService;
    private final CompanyService companyService;

    @Autowired
    public MainController(RoleService roleService, UserService userService, GenreService genreService, CompanyService companyService) {
        this.roleService = roleService;
        this.userService = userService;
        this.genreService = genreService;
        this.companyService = companyService;
    }


    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String redirectToLoginPage() {
        return "redirect:/login";
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public ModelAndView showLoginPage() throws NoHandlerFoundException {

        return new ModelAndView("login");
    }

    @RequestMapping(value = {"/translation"}, method = RequestMethod.GET)
    public ModelAndView showPlayerPage() throws NoHandlerFoundException {

        return new ModelAndView("translation");
    }


    @RequestMapping(value = "/googleAuth")
    public String GoogleAuthorization() {
        final String redirectUri = "http://localhost:8080/google";
        final String clientId = "302577676814-07bvhpq9k8r5qngg223csrf81d17qu9j.apps.googleusercontent.com";
        final String responseType = "code";
        final String scope = "https://www.googleapis.com/auth/userinfo.email";
        StringBuilder url = new StringBuilder();
        url.append("https://accounts.google.com/o/oauth2/auth?redirect_uri=")
                .append(redirectUri)
                .append("&response_type=")
                .append(responseType)
                .append("&client_id=")
                .append(clientId)
                .append("&scope=")
                .append(scope);
        return "redirect:" + url.toString();
    }

    @RequestMapping(value = "/google")
    public String GoogleAuthorization(@RequestParam("code") String code) throws IOException {
        final String clientId = "302577676814-07bvhpq9k8r5qngg223csrf81d17qu9j.apps.googleusercontent.com";
        final String clientSecret = "VADOrvY9rXwm5N-1Y1zdU-rq";
        final String redirectUri = "http://localhost:8080/google";
        final HttpTransport transport = new NetHttpTransport();
        final JacksonFactory jsonFactory = new JacksonFactory();

        GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(transport, jsonFactory,
                clientId, clientSecret, code, redirectUri).execute();

        GoogleIdToken idToken = tokenResponse.parseIdToken();
        GoogleIdToken.Payload payload = idToken.getPayload();
        String email = payload.getEmail();
        String googleId = payload.getSubject();
        User user = userService.getUserByGoogleId(googleId);
        if (user == null) {
            Role role = roleService.getRoleById((long) 2);
            Set<Role> roleSet = new HashSet<>();
            roleSet.add(role);
            user = new User(googleId, email, roleSet, true);
            user.setCompany(companyService.getById(1L));
            userService.addUser(user);
            user = userService.getUserByGoogleId(googleId);
        }
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        return "redirect:/user";
    }
}