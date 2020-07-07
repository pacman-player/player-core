package spring.app.util;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import spring.app.model.Response;
import spring.app.util.BuilderAnswerForms.Builders.ErrorMessageBuilder;
import spring.app.util.BuilderAnswerForms.Builders.SuccessMessageBuilder;
import spring.app.util.BuilderAnswerForms.DirectorAnswerBuilder;

@Component
public class ResponseBuilder<T> extends ResponseEntityExceptionHandler {
    T data = null;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    DirectorAnswerBuilder<T> directorAnswerBuilder = new DirectorAnswerBuilder<T>();

    public Response<T> error(String message) {
        directorAnswerBuilder.setStatus(HttpStatus.BAD_REQUEST);
        directorAnswerBuilder.setMessage(message);
        ErrorMessageBuilder<T> errorBuilder = new ErrorMessageBuilder<>();
        directorAnswerBuilder.constructErrorMessage(errorBuilder);
        return errorBuilder.getResponse();
    }

    public Response<T> Error(Exception ex) {
        directorAnswerBuilder.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        directorAnswerBuilder.setMessage("prosto exception");
        directorAnswerBuilder.setDebugMessage(ex);
        ErrorMessageBuilder<T> errorBilder = new ErrorMessageBuilder<>();
        directorAnswerBuilder.constructErrorMessage(errorBilder);
        return errorBilder.getResponse();
    }

    public Response<T> success(T data) {
        directorAnswerBuilder.setStatus(HttpStatus.OK);
        directorAnswerBuilder.setData(data);
        SuccessMessageBuilder<T> successBilder = new SuccessMessageBuilder<>();
        directorAnswerBuilder.constructSuccessMessage(successBilder);
        return successBilder.getResponse();
    }
}
