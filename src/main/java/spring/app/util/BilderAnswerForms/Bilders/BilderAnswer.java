package spring.app.util.BilderAnswerForms.Bilders;

import org.springframework.http.ResponseEntity;

public interface BilderAnswer {
    void setSuccessFlag(Boolean successFlag);
    void setCodeMessage(int messageCode);
    void setTextMessage(String TextMassage);
    void setMetaMessage(String MetaMessage);
    void setDataMessage(String DataMessage);
    ResponseEntity<Object> getResultmessage();

}
