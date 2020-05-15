package spring.app.util.BilderAnswerForms.Bilders;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface BilderAnswer {
    void setSuccessFlag(Boolean successFlag);

    void setErrorMessage(HttpStatus status, String textMessage);

    void setDataMessage(String TextMassage);

    void setMetaMessage(String MetaMessage);


}