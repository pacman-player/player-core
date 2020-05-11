package spring.app.util;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import spring.app.model.Response;
import spring.app.util.BilderAnswerForms.Bilders.ErrorMessageBilder;
import spring.app.util.BilderAnswerForms.Bilders.SuccessMessageBilder;
import spring.app.util.BilderAnswerForms.DirectorAnswerBilder;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ResponseBilder<T> extends ResponseEntityExceptionHandler {
    T data = null;
    HttpStatus httpStatus;


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    DirectorAnswerBilder directorAnswerBilder = new DirectorAnswerBilder();


    @ExceptionHandler(Exception.class)
    public Response Error(Exception ex, WebRequest request) {

        directorAnswerBilder.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        directorAnswerBilder.setMessage("prosto exception");
        directorAnswerBilder.setDebugMessage(ex);

        ErrorMessageBilder errorBilder = new ErrorMessageBilder();
        directorAnswerBilder.constructErrorMessage(errorBilder);

        return errorBilder.getResponse();
    }

    public Response success(T data, WebRequest request) {

        directorAnswerBilder.setStatus(HttpStatus.OK);
        directorAnswerBilder.setData(data);
        SuccessMessageBilder successBilder = new SuccessMessageBilder();
        directorAnswerBilder.constructSuccessMessage(successBilder);


        return successBilder.getResponse();
    }

}
