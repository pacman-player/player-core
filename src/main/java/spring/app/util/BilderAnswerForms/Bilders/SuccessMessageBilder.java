package spring.app.util.BilderAnswerForms.Bilders;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import spring.app.model.ErrorMessage;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessMessageBilder<T> implements BilderAnswer {
    private boolean successFlag;
    private ErrorMessage errorMessage;
    private T data;
    private String MetaMessage;
    private String DataMessage;

    @Override
    public void setSuccessFlag(Boolean successFlag) {

    }

    @Override
    public void setErrorMessage(HttpStatus status, String textMessage) {

    }


    @Override
    public void setMetaMessage(String MetaMessage) {

    }

    @Override
    public void setData(Object data) {

    }

    @Override
    public void setDataMessage(String DataMessage) {

    }

}
