package spring.app.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import spring.app.util.BilderAnswerForms.Bilders.ErrorMessageBilder;
import spring.app.util.BilderAnswerForms.DirectorAnswerBilder;

import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class Response<T> extends ResponseEntityExceptionHandler {
    T data = null;
    HttpStatus httpStatus;


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    DirectorAnswerBilder directorAnswerBilder = new  DirectorAnswerBilder();



    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> Eror(Exception ex, WebRequest request) {

        directorAnswerBilder.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        directorAnswerBilder.setMessage("prosto exception");
        directorAnswerBilder.setDebugMessage(ex);

        ErrorMessageBilder errorBilder = new ErrorMessageBilder();
        directorAnswerBilder.constructErrorMessage(errorBilder);
        ResponseEntity responseEntity = new ResponseEntity<>(errorBilder, HttpStatus.INTERNAL_SERVER_ERROR);
        return responseEntity;
    }
}
