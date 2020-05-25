package spring.app.util.BuilderAnswerForms.Builders;

import org.springframework.http.HttpStatus;
import spring.app.model.Response;

public interface BuilderAnswer<T> {
    void setSuccessFlag(Boolean successFlag);

    void setErrorMessage(HttpStatus status, String textMessage);

    void setData(T data);

    Response<T> getResponse();

}
