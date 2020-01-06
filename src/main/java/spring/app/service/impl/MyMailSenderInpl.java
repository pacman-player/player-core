package spring.app.service.impl;

import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;
import spring.app.dao.abstraction.UserDao;
import spring.app.model.User;
import spring.app.service.abstraction.MyMailSender;

import java.util.List;

@Component
public class MyMailSenderInpl implements MyMailSender {

    private final JavaMailSender emailSender;
    private final UserDao userDao;

    @Autowired
    public MyMailSenderInpl(JavaMailSender emailSender, UserDao userDao) {
        this.emailSender = emailSender;
        this.userDao = userDao;
    }

    @Override
    public void send(String nameCompilation) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        List<User> users = userDao.getAll();

        if(users != null) {
            for (User user : users) {
                String message = String.format(
                        "Привет, %s! \n" +
                                "Унас появилась новая подборка %s",
                        user.getFirstName(),
                        nameCompilation
                );
                mailMessage.setFrom(user.getFirstName());
                mailMessage.setTo(user.getEmail());
                mailMessage.setSubject("Новая подборка");
                mailMessage.setText(message);

                emailSender.send(mailMessage);
            }
        }
    }
}
