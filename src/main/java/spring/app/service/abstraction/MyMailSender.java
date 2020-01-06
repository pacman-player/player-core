package spring.app.service.abstraction;

public interface MyMailSender {
    public void send(String emailTo, String subject, String message, String  emailFrom);
}
