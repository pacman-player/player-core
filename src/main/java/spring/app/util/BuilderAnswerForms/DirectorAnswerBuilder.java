package spring.app.util.BuilderAnswerForms;


import org.springframework.http.HttpStatus;
import spring.app.util.BuilderAnswerForms.Builders.BuilderAnswer;

public class DirectorAnswerBuilder<T>{

    private HttpStatus status;
    private String message;
    private String debugMessage;
    private T data;

    public void setData(T data) {
        this.data = data;
    }



    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDebugMessage(Throwable ex) {
        this.debugMessage = ex.toString();
    }


    public void constructErrorMessage(BuilderAnswer<T> builderAnswer){
        builderAnswer.setSuccessFlag(false);
        builderAnswer.setErrorMessage(status,message);

    }

    public void constructSuccessMessage(BuilderAnswer<T> builderAnswer){
        builderAnswer.setSuccessFlag(true);
        builderAnswer.setData(data);
    }

    /**
     * Директор знает в какой последовательности заставлять работать строителя. Он
     * работает с ним через общий интерфейс Строителя. Из-за этого, он может не
     * знать какой конкретно продукт сейчас строится.
     */

}

