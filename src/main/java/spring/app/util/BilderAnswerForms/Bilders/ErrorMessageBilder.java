package spring.app.util.BilderAnswerForms.Bilders;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import spring.app.model.ErrorMessage;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorMessageBilder implements BilderAnswer {
    private boolean successFlag;
    private ErrorMessage errorMessage = new ErrorMessage();

    private String MetaMessage;
    private String DataMessage;

    @Override
    public void setSuccessFlag(Boolean successFlag) {

    }

    @Override
    public void setErrorMessage(HttpStatus status, String textMassage) {
        this.errorMessage.setCodeMessage(status);
        this.errorMessage.setTextMessage(textMassage);
    }

    @Override
    public void setMetaMessage(String MetaMessage) {

    }

    public boolean isSuccessFlag() {
        return successFlag;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public String getMetaMessage() {
        return MetaMessage;
    }

    public String getDataMessage() {
        return DataMessage;
    }


    @Override
    public void setDataMessage(String DataMessage) {
        this.DataMessage = DataMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ErrorMessageBilder that = (ErrorMessageBilder) o;

        if (successFlag != that.successFlag) return false;
        if (errorMessage != null ? !errorMessage.equals(that.errorMessage) : that.errorMessage != null) return false;
        if (MetaMessage != null ? !MetaMessage.equals(that.MetaMessage) : that.MetaMessage != null) return false;
        return DataMessage != null ? DataMessage.equals(that.DataMessage) : that.DataMessage == null;
    }

    @Override
    public int hashCode() {
        int result = (successFlag ? 1 : 0);
        result = 31 * result + (errorMessage != null ? errorMessage.hashCode() : 0);
        result = 31 * result + (MetaMessage != null ? MetaMessage.hashCode() : 0);
        result = 31 * result + (DataMessage != null ? DataMessage.hashCode() : 0);
        return result;
    }

}
