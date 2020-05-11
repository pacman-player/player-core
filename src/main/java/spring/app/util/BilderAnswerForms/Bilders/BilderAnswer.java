package spring.app.util.BilderAnswerForms.Bilders;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import spring.app.model.Response;

public interface BilderAnswer<T> {
    void setSuccessFlag(Boolean successFlag);

    void setErrorMessage(HttpStatus status, String textMessage);

    void setData(T data);

    Response getResponse();

}
