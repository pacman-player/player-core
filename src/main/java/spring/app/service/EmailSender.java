package spring.app.service;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailSender {

    private JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String username;

    public void send(String emailTo, String subject, String message){
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(username);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        emailSender.send(mailMessage);
    }

//    String to = "evgenykalashnikov26gmail.com";
//    String from = "pacmancore26@gmail.com";
//    String host = "127.0.0.1";
//
//    private String username;
//    private String password;
//    private Properties properties;
//
//    public EmailSender(String username, String password) throws AddressException, MessagingException {
//        this.username = username;
//        this.password = password;
//
//        properties = new Properties();
//        properties.put("mail.smtp.auth", "true");
//        properties.put("mail.smtp.starttls.enable", "true");
//        properties.put("mail.smtp.host", "smtp.gmail.com");
//        properties.put("mail.smtp.port", "587");
//    }
//
//    public void send(String subject, String text, String fromEmail, String toEmail){
//        Session session = Session.getInstance(properties, new Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(username, password);
//            }
//        });
//
//        try {
//            Message message = new MimeMessage(session);
//            //от кого
//            message.setFrom(new InternetAddress(username));
//            //кому
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
//            //Заголовок письма
//            message.setSubject(subject);
//            //Содержимое
//            message.setText(text);
//
//            //Отправляем сообщение
//            Transport.send(message);
//        } catch (MessagingException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
