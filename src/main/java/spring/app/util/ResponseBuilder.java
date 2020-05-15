package spring.app.util;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import spring.app.model.Response;
import spring.app.util.BilderAnswerForms.Bilders.ErrorMessageBilder;
import spring.app.util.BilderAnswerForms.Bilders.SuccessMessageBilder;
import spring.app.util.BilderAnswerForms.DirectorAnswerBilder;

@Component
public class ResponseBuilder<T> extends ResponseEntityExceptionHandler {
    T data = null;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    DirectorAnswerBilder<T> directorAnswerBilder = new DirectorAnswerBilder<T>();


    public Response<T> Error(Exception ex) {
        directorAnswerBilder.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        directorAnswerBilder.setMessage("prosto exception");
        directorAnswerBilder.setDebugMessage(ex);
        ErrorMessageBilder<T> errorBilder = new ErrorMessageBilder<>();
        directorAnswerBilder.constructErrorMessage(errorBilder);
        return errorBilder.getResponse();
    }

    public Response<T> success(T data) {
            directorAnswerBilder.setStatus(HttpStatus.OK);
            directorAnswerBilder.setData(data);
            SuccessMessageBilder<T> successBilder = new SuccessMessageBilder<>();
            directorAnswerBilder.constructSuccessMessage(successBilder);
            return successBilder.getResponse();
    }
}
