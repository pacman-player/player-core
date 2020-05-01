package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import spring.app.dto.UserDto;
import spring.app.service.abstraction.SendEmailAboutAddNewCompilation;
import spring.app.service.abstraction.UserService;

import java.util.List;

@Service
public class SendEmailAboutAddNewCompilationImpl implements SendEmailAboutAddNewCompilation {

    private final JavaMailSender emailSender;
    private final UserService userService;

    @Autowired
    public SendEmailAboutAddNewCompilationImpl(JavaMailSender emailSender, UserService userService) {
        this.emailSender = emailSender;
        this.userService = userService;
    }

    @Override
    public void send(String nameCompilation) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        List<UserDto> users = userService.getAllUsers();


        if (users != null) {
            for (UserDto userDto : users) {
                String message = String.format(
                        "Привет, %s! \n" +
                                "У нас появилась новая подборка %s",
                        userDto.getLogin(),
                        nameCompilation
                );
                mailMessage.setFrom(userDto.getLogin());
                mailMessage.setTo(userDto.getEmail());
                mailMessage.setSubject("Новая подборка");
                mailMessage.setText(message);
//                TODO раскомментировать строку для включения отправки писем (нужна своя почта (для тестов))
//                emailSender.send(mailMessage);
            }
        }
    }
}
