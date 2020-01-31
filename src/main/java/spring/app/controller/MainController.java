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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import spring.app.model.Company;
import spring.app.model.PlayList;
import spring.app.model.Role;
import spring.app.model.User;
import spring.app.service.abstraction.*;
import spring.app.util.UserValidator;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Controller("/test")
public class MainController {
    private final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    private final RoleService roleService;
    private final UserService userService;
    private final GenreService genreService;
    private final CompanyService companyService;
    private final OrgTypeService orgTypeService;
    private final PlayListService playListService;
    private final AddressService addressService;
    private final UserValidator userValidator;

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
    public MainController(RoleService roleService, UserService userService, GenreService genreService, CompanyService companyService, OrgTypeService orgTypeService, PlayListService playListService, AddressService addressService, UserValidator userValidator) {
        this.roleService = roleService;
        this.userService = userService;
        this.genreService = genreService;
        this.companyService = companyService;
        this.orgTypeService = orgTypeService;
        this.playListService = playListService;
        this.addressService = addressService;
        this.userValidator = userValidator;
    }

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String redirectToLoginPage() {
        return "redirect:/login";
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public ModelAndView showLoginPage(HttpSession httpSession) {
        //получаем error из LoginController
        String errorFromBindingResult = (String) httpSession.getAttribute("error");
        ModelAndView modelAndView = new ModelAndView("login");
            if (errorFromBindingResult != null) {
                //добавляем сообщение об ошибке во вьюху
                modelAndView.addObject("error", errorFromBindingResult);
            }
        return modelAndView;
    }

    @RequestMapping(value = {"/login-captcha"}, method = RequestMethod.GET)
    public ModelAndView showLoginPageCaptcha() {
        return new ModelAndView("login-captcha");
    }

    @RequestMapping(value = {"/translation"}, method = RequestMethod.GET)
    public ModelAndView showPlayerPage() {

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
            userService.addUser(user);

            //здесь сетим дефолтную компанию
            Company company = new Company();
            String companyName = "Default(" + UUID.randomUUID().toString() + ")";
            company.setName(companyName);
            company.setStartTime(LocalTime.of(11, 0));
            company.setCloseTime(LocalTime.of(23, 0));
            company.setOrgType(orgTypeService.getOrgTypeById(1L));
            company.setUser(userService.getUserByGoogleId(googleId));

            //сетим утренний плейлист
            PlayList morningPlayList = new PlayList();
            morningPlayList.setName("Morning playlist");
            playListService.addPlayList(morningPlayList);
            Set<PlayList> morningPlaylistSet = new HashSet<>();
            morningPlaylistSet.add(morningPlayList);
            company.setMorningPlayList(morningPlaylistSet);

            //сетим дневной плейлист
            PlayList middayPlayList = new PlayList();
            middayPlayList.setName("Midday playlist");
            playListService.addPlayList(middayPlayList);
            Set<PlayList> middayPlaylistSet = new HashSet<>();
            middayPlaylistSet.add(middayPlayList);
            company.setMiddayPlayList(middayPlaylistSet);

            //сетим вечерний плейлист
            PlayList eveningPlayList = new PlayList();
            eveningPlayList.setName("Evening playlist");
            playListService.addPlayList(eveningPlayList);
            Set<PlayList> eveningPlaylistSet = new HashSet<>();
            eveningPlaylistSet.add(eveningPlayList);
            company.setEveningPlayList(eveningPlaylistSet);

            companyService.addCompany(company);
            user.setCompany(companyService.getByCompanyName(companyName));

            user = userService.getUserByGoogleId(googleId);
        }
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        return "redirect:/user";
    }

    @GetMapping("/player")
    public String player() {
        return "player";
    }

    @GetMapping(value = "/vkAuth")
    public String vkAuthorization() {
        String url = "https://oauth.vk.com/authorize?client_id=" + appId +
                "&display=page&redirect_uri=" + redirectUri +
                "&scope=status,email&response_type=code&v=5.103";
        return "redirect:" + url;
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
            user = new User(list.get(0).getId(),
                    new String(list.get(0).getFirstName().getBytes(StandardCharsets.UTF_8)),
                    new String(list.get(0).getLastName().getBytes(StandardCharsets.UTF_8)),
                    authResponse.getEmail(),
                    roleSet,
                    companyService.getById(1L),
                    true);
            userService.addUser(user);

            //здесь сетим дефолтную компанию
            Company company = new Company();
            String companyName = "Default(" + UUID.randomUUID().toString() + ")";
            company.setName(companyName);
            company.setStartTime(LocalTime.of(11, 0));
            company.setCloseTime(LocalTime.of(23, 0));
            company.setOrgType(orgTypeService.getOrgTypeById(1L));
            company.setUser(userService.getUserByVkId(actor.getId()));

            //сетим утренний плейлист
            PlayList morningPlayList = new PlayList();
            morningPlayList.setName("Morning playlist");
            playListService.addPlayList(morningPlayList);
            Set<PlayList> morningPlaylistSet = new HashSet<>();
            morningPlaylistSet.add(morningPlayList);
            company.setMorningPlayList(morningPlaylistSet);

            //сетим дневной плейлист
            PlayList middayPlayList = new PlayList();
            middayPlayList.setName("Midday playlist");
            playListService.addPlayList(middayPlayList);
            Set<PlayList> middayPlaylistSet = new HashSet<>();
            middayPlaylistSet.add(middayPlayList);
            company.setMiddayPlayList(middayPlaylistSet);

            //сетим вечерний плейлист
            PlayList eveningPlayList = new PlayList();
            eveningPlayList.setName("Evening playlist");
            playListService.addPlayList(eveningPlayList);
            Set<PlayList> eveningPlaylistSet = new HashSet<>();
            eveningPlaylistSet.add(eveningPlayList);
            company.setEveningPlayList(eveningPlaylistSet);

            companyService.addCompany(company);
            user.setCompany(companyService.getByCompanyName(companyName));

            user = userService.getUserByVkId(actor.getId());
        }
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        return "redirect:/user";
    }
}