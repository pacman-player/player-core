package spring.app.util.BilderAnswerForms.Bilders;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.ResponseEntity;
import spring.app.model.ErrorMessage;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessMessageBilder implements BilderAnswer {
    private boolean successFlag;
    private ErrorMessage errorMessage;
    private Integer codeMassage;
    private String TextMassage;
    private String MetaMessage;
    private String DataMessage;

    @Override
    public void setSuccessFlag(Boolean successFlag) {

    }

    @Override
    public void setCodeMessage(int messageCode) {

    }

    @Override
    public void setTextMessage(String TextMassage) {

    }

    @Override
    public void setMetaMessage(String MetaMessage) {

    }

    @Override
    public void setDataMessage(String DataMessage) {

    }

    @Override
    public ResponseEntity<Object> getResultmessage() {
        return null;
    }
}
