package spring.app.util.BuilderAnswerForms.Builders;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
import spring.app.model.ErrorMessage;
import spring.app.model.Response;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorMessageBuilder<T> implements BuilderAnswer<T> {
    private boolean successFlag;
    private ErrorMessage errorMessage = new ErrorMessage();


    @Override
    public void setSuccessFlag(Boolean successFlag) {
    this.successFlag = successFlag;
    }

    @Override
    public void setErrorMessage(HttpStatus status, String textMassage) {
        this.errorMessage.setCodeMessage(status.value());
        this.errorMessage.setTextMessage(textMassage);
    }

    @Override
    public void setData(T data) {
    }

    public boolean isSuccessFlag() {
        return successFlag;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }


    public Response<T> getResponse(){
        Response<T> response = new Response<>();
        response.setSuccess(successFlag);
        response.setErrorMessage(errorMessage);
        return response;
    }


}
