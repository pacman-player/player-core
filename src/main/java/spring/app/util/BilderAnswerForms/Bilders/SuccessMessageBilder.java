package spring.app.util.BilderAnswerForms.Bilders;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
import spring.app.model.ErrorMessage;
import spring.app.model.Response;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessMessageBilder<T> implements BilderAnswer {
    private boolean successFlag;
    private ErrorMessage errorMessage;
    private T data;
    private String DataMessage;

    @Override
    public void setSuccessFlag(Boolean successFlag) {
    this.successFlag = successFlag;
    }

    @Override
    public void setErrorMessage(HttpStatus status, String textMessage) {

    }

    @Override
    public void setData(Object data) {
    this.data = (T)data;
    }

    @Override
    public Response getResponse() {
        Response response = new Response();
        response.setSuccess(successFlag);
        response.setData(data);
        return response;
    }


}
