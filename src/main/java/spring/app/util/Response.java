package spring.app.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import spring.app.util.BilderAnswerForms.Bilders.ErrorMessageBilder;
import spring.app.util.BilderAnswerForms.DirectorAnswerBilder;

@ControllerAdvice
public class Response extends ResponseEntityExceptionHandler {

    DirectorAnswerBilder directorAnswerBilder;


    @Autowired
    public void setDirectorAnswerBilder(DirectorAnswerBilder directorAnswerBilder) {
        this.directorAnswerBilder = directorAnswerBilder;
    }



    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {

        directorAnswerBilder.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        directorAnswerBilder.setMessage("prosto exception");
        directorAnswerBilder.setDebugMessage(ex);

        ErrorMessageBilder errorBilder = new ErrorMessageBilder();
        directorAnswerBilder.constructErorMessage(errorBilder);

        return new ResponseEntity<>(errorBilder, HttpStatus.INTERNAL_SERVER_ERROR);
    }}
