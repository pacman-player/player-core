package spring.app.controller.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import spring.app.service.EmailSender;
import spring.app.service.abstraction.UserService;
import spring.app.model.User;

import java.nio.charset.Charset;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;


@RestController
@RequestMapping(value = "/api/user/edit/")
public class UserEditRestController {

    private String PASSWORD = "";

    private final UserService userService;

    @Autowired
    public UserEditRestController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public EmailSender emailSender;

    @GetMapping(value = "/get_user")
    public User getUserData(){
        return ((User) getContext().getAuthentication().getPrincipal());
    }

    @PutMapping(value = "/edit_data")
    public ResponseEntity<User> editUserData(@RequestBody User newUser){
        User user = ((User) getContext().getAuthentication().getPrincipal());
        if(!newUser.getLogin().equals(user.getLogin())) {
            if (userService.getUserByLogin(newUser.getLogin()) == null) {
                user.setLogin(newUser.getLogin());
            }else{
                return ResponseEntity.badRequest().body(user);
            }
        }
        if(!newUser.getEmail().equals(user.getEmail())){
            if(userService.getUserByEmail(newUser.getEmail()) == null){
                user.setEmail(newUser.getEmail());
            }else{
                return ResponseEntity.badRequest().body(user);
            }
        }
        userService.updateUser(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping(value = "/edit_pass")
    public void editUserPass(@RequestBody String newPassword){
        newPassword = newPassword.substring(1, newPassword.length()-1);
        newPassword = newPassword.replaceAll("##@##"  , "\"");
        newPassword = newPassword.replaceAll("##@@##"  ,"\\\\");

        User user = ((User) getContext().getAuthentication().getPrincipal());
        user.setPassword(newPassword);
        userService.updateUser(user);
        sendMail(user);
    }

    @GetMapping(value = "/code_check")
    public boolean codeCheck(String code){
        if(code.equals(PASSWORD)){
            return true;
        }
        return false;
    }

    private static int randomNum(int min, int max){
        Random r = new Random();
        return r.nextInt(max) + min;
    }

    public void sendMail(User user){
        if(!StringUtils.isEmpty(user.getEmail())){
            String[] data = {
                    "a","b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
                    "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
                    "1", "2", "3", "4", "5", "6", "7", "8", "9"
            };
            int length = randomNum(4, 10);
            String[] p = new String[length];

            for(int i = 0; i < length; i++){
                p[i] = data[randomNum(0, data.length)];
            }
//            String password = "";
            for(String s : p){
                PASSWORD += s;
            }
            System.out.println(PASSWORD);

            String message = String.format(
                    "Здравствуйте, %s! \n" +
                            "Для смены пароля введите код подтверждения: " +
                            PASSWORD + "\n" +
                            "Если вы не запрашивали смену пароля, свяжитесь со службой технической поддержки.",
                    user.getLogin()
            );
            emailSender.send("evgenykalashnikov26@gmail.com", "Test", message);
        }
    }
}
