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
        this.debugMessage = ex.getLocalizedMessage();
    }


    public void constructErorMessage(BilderAnswer bilderAnswer){
        bilderAnswer.setSuccessFlag(false);
        bilderAnswer.setCodeMessage(status.value());
        bilderAnswer.setTextMessage(message);
        bilderAnswer.setDataMessage(debugMessage);
    }

    /**
     * Директор знает в какой последовательности заставлять работать строителя. Он
     * работает с ним через общий интерфейс Строителя. Из-за этого, он может не
     * знать какой конкретно продукт сейчас строится.
     */

}

