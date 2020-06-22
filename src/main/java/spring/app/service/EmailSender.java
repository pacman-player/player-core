package spring.app.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailSender {

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.port}")
    private int port;

    private JavaMailSender emailSender;

    public EmailSender(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void send(String emailTo, String subject, String message){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailMessage.setFrom(username);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        Properties properties = mailSender.getJavaMailProperties();
        properties.setProperty("mail.smtp.auth", "true");

        emailSender.send(mailMessage);
    }
}
