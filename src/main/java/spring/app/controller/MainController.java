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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import spring.app.dto.CaptchaResponseDto;
import spring.app.model.*;
import spring.app.service.abstraction.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.*;

@Controller("/test")
public class MainController {
    private final Logger LOGGER = LoggerFactory.getLogger(MainController.class);
    private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";
    private final RoleService roleService;
    private final UserService userService;
    private final CompanyService companyService;
    private final OrgTypeService orgTypeService;
    private final PlayListService playListService;
    private final AddressService addressService;
    private RestTemplate restTemplate = new RestTemplate();

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
    @Value("${recaptcha.secret}")
    private String secret;

    @Autowired
    public MainController(RoleService roleService,
                          UserService userService,
                          CompanyService companyService,
                          OrgTypeService orgTypeService,
                          PlayListService playListService,
                          AddressService addressService) {
        this.roleService = roleService;
        this.userService = userService;
        this.companyService = companyService;
        this.orgTypeService = orgTypeService;
        this.playListService = playListService;
        this.addressService = addressService;
    }

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String redirectToLoginPage() {
        return "redirect:/login";
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public ModelAndView showLoginPage(HttpSession httpSession) {
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

    @PostMapping("/login-captcha")
    public void loginCaptcha(
            HttpServletResponse response,
            HttpServletRequest request,
            @RequestParam("g-recaptcha-response") String captchaResponce,
            Model model) throws IOException, ServletException {
        String url = String.format(CAPTCHA_URL, secret, captchaResponce);
        CaptchaResponseDto captchaResponse = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);

        if (!captchaResponse.isSuccess()) {
            //        вывод сообщения в html, если капча не введена
            model.addAttribute("captchaError", "Fill captcha");
            response.sendRedirect("/login-captcha");
        } else {
            request.getRequestDispatcher("/processing-url").forward(request, response);
        }
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
    public String GoogleAuthorization(@RequestParam("code") String code, Model model) throws IOException {
        final HttpTransport transport = new NetHttpTransport();
        final JacksonFactory jsonFactory = new JacksonFactory();
        LOGGER.info("Google code auth is {}", code);
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
            company.setOrgType(orgTypeService.getByName("Кальян-бар"));
            company.setUser(userService.getUserByGoogleId(googleId));

            //cетим дефолтный адрес компании
            Address defaultAddress = new Address("Country", "City", "Street", "House", 0.0, 0.0);
            addressService.addAddress(defaultAddress);
            company.setAddress(defaultAddress);

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
//            user.setCompany(companyService.getByCompanyName(companyName));
            user.setCompany(company);

            user = userService.getUserByGoogleId(googleId);
            LOGGER.info("New user registered by google {}", user);
        }
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        if (userService.getUserByGoogleId(googleId).isEnabled()) {
//            return "redirect:/user";
            return "redirect:/user/spa";
        } else {
            model.addAttribute("error", "Ваш аккаунт забанен");
            return "/login";
        }
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
    public String vkAuthorization(@RequestParam("code") String code, Model model) throws ClientException, ApiException {
        LOGGER.info("VK auth code is {}", code);
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
                    companyService.getByCompanyName("Pacman"),
                    true);
            user.setLogin("vkAuth");
            userService.addUser(user);

            //здесь сетим дефолтную компанию
            Company company = new Company();
            String companyName = "Default(" + UUID.randomUUID().toString() + ")";
            company.setName(companyName);
            company.setStartTime(LocalTime.of(11, 0));
            company.setCloseTime(LocalTime.of(23, 0));
            company.setOrgType(orgTypeService.getByName("Кальян-бар"));
            company.setUser(userService.getUserByVkId(actor.getId()));

            //cетим дефолтный адрес компании
            Address defaultAddress = new Address("Country", "City", "Street", "House", 0.0, 0.0);
            addressService.addAddress(defaultAddress);
            company.setAddress(defaultAddress);

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
            LOGGER.info("New user registered by VK - {}", user);
        }
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        if (userService.getUserByVkId(actor.getId()).isEnabled()) {
//            return "redirect:/user";
            return "redirect:/user/spa";
        } else {
            model.addAttribute("error", "Ваш аккаунт забанен");
            return "/login";
        }
    }
}