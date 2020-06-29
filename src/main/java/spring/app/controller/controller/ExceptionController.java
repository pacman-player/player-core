package spring.app.controller.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import spring.app.model.Response;
import spring.app.util.ResponseBuilder;

import javax.servlet.http.HttpServletRequest;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ExceptionController<T> {
    ResponseBuilder<T> responseBuilder;

    @Autowired
    public void setResponseBuilder(ResponseBuilder<T> responseBuilder) {
        this.responseBuilder = responseBuilder;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    Response<T>
    handleBadRequest(HttpServletRequest req, Exception ex) {
        ResponseBuilder<T> responseBuilder = new ResponseBuilder<T>();
        return responseBuilder.Error(ex);
    }
}
