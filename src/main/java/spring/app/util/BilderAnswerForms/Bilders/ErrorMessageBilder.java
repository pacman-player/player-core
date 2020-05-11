package spring.app.util.BilderAnswerForms.Bilders;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
import spring.app.model.ErrorMessage;
import spring.app.model.Response;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorMessageBilder implements BilderAnswer {
    private boolean successFlag;
    private ErrorMessage errorMessage = new ErrorMessage();

    @Override
    public void setSuccessFlag(Boolean successFlag) {
    this.successFlag = successFlag;
    }

    @Override
    public void setErrorMessage(HttpStatus status, String textMassage) {
        this.errorMessage.setCodeMessage(status);
        this.errorMessage.setTextMessage(textMassage);
    }

    @Override
    public void setData(Object data) {

    }

    public boolean isSuccessFlag() {
        return successFlag;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }


    public Response getResponse(){
        Response response = new Response();
        response.setSuccess(successFlag);
        response.setErrorMessage(errorMessage);
        return response;
    }


}
