package spring.app.util.BilderAnswerForms;


import org.springframework.http.HttpStatus;
import spring.app.util.BilderAnswerForms.Bilders.BilderAnswer;

public class DirectorAnswerBilder {

    private HttpStatus status;
    private String message;
    private String debugMessage;

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDebugMessage(Throwable ex) {
        this.debugMessage = ex.toString();
    }


    public void constructErrorMessage(BilderAnswer bilderAnswer){
        bilderAnswer.setSuccessFlag(false);
        bilderAnswer.setErrorMessage(status,message);
        bilderAnswer.setDataMessage(debugMessage);
    }

    /**
     * Директор знает в какой последовательности заставлять работать строителя. Он
     * работает с ним через общий интерфейс Строителя. Из-за этого, он может не
     * знать какой конкретно продукт сейчас строится.
     */

}

