package spring.app.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import spring.app.model.Role;
import spring.app.model.User;
import spring.app.service.abstraction.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller("/test")
@PropertySource("classpath:application.properties")
public class MainController {
    private final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    private final RoleService roleService;
    private final UserService userService;
    private final GenreService genreService;
    private final CompanyService companyService;
    private final OrgTypeService orgTypeService;

    @Value("${googleRedirectUri}")
    private String googleRedirectUri;
    @Value("${googleClientId}")
    private String googleClientId;
    @Value("${googleResponseType}")
    private String googleResponseType;
    @Value("${googleScope}")
    private String googleScope;
    @Value("${googleClientSecret}")
    private String googleClientSecret;

    @Value("${vkAppId}")
    private String appId;
    @Value("${vkClientSecret}")
    private String clientSecret;
    @Value("${vkRedirectUri}")
    private String redirectUri;

    @Autowired
    public MainController(RoleService roleService, UserService userService, GenreService genreService, CompanyService companyService, OrgTypeService orgTypeService) {
        this.roleService = roleService;
        this.userService = userService;
        this.genreService = genreService;
        this.companyService = companyService;
        this.orgTypeService = orgTypeService;
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

        StringBuilder url = new StringBuilder();
        url.append("https://accounts.google.com/o/oauth2/auth?redirect_uri=")
                .append(googleRedirectUri)
                .append("&response_type=")
                .append(googleResponseType)
                .append("&client_id=")
                .append(googleClientId)
                .append("&scope=")
                .append(googleScope);
        return "redirect:" + url.toString();
    }

    @RequestMapping(value = "/google")
    public String GoogleAuthorization(@RequestParam("code") String code) throws IOException {
        final HttpTransport transport = new NetHttpTransport();
        final JacksonFactory jsonFactory = new JacksonFactory();

        GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(transport, jsonFactory,
                googleClientId, googleClientSecret, code, googleRedirectUri).execute();

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

    @GetMapping(value = "/vkAuth")
    public String vkAuthorization() {
        StringBuilder url = new StringBuilder();
        url.append("https://oauth.vk.com/authorize?client_id=").append(appId)
                .append("&display=page&redirect_uri=").append(redirectUri)
                .append("&scope=status,email&response_type=code&v=5.103");
        return "redirect:" + url.toString();
    }

    @GetMapping(value = "/vkontakte")
    public String vkAuthorization(@RequestParam("code") String code) throws ClientException, ApiException {
        TransportClient transportClient = HttpTransportClient.getInstance();
        VkApiClient vk = new VkApiClient(transportClient);
        UserAuthResponse authResponse = vk.oauth()
                .userAuthorizationCodeFlow(Integer.parseInt(appId), clientSecret, redirectUri, code)
                .execute();
        UserActor actor = new UserActor(authResponse.getUserId(), authResponse.getAccessToken());
        User user = userService.getUserByVkId(actor.getId());
        if (user == null) {
            Role role = roleService.getRoleById((long) 2);
            Set<Role> roleSet = new HashSet<>();
            roleSet.add(role);
            List<UserXtrCounters> list = vk.users().get(actor).execute();
            user = new User(list.get(0).getFirstName(),
                    list.get(0).getLastName(),
                    list.get(0).getId(),
                    roleSet,
                    companyService.getById(1L),
                    true);
            userService.addUser(user);
            user = userService.getUserByVkId(actor.getId());
        }
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        return "redirect:/user";
    }
}