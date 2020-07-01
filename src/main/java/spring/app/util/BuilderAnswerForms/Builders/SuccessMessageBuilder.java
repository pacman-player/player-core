package spring.app.util.BuilderAnswerForms.Builders;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
import spring.app.model.ErrorMessage;
import spring.app.model.Response;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessMessageBuilder<T> implements BuilderAnswer<T> {
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
    public void setData(T data) {
        this.data = data;
    }

    @Override
    public Response<T> getResponse() {
        Response<T> response = new Response<T>();
        response.setSuccess(successFlag);
        response.setData(data);
        return response;
    }


}
