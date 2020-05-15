package spring.app.model;

import org.springframework.http.HttpStatus;

public class ErrorMessage {
    private HttpStatus codeMessage;
    private String textMessage;

    public HttpStatus getCodeMessage() {
        return codeMessage;
    }

    public void setCodeMessage(HttpStatus codeMessage) {
        this.codeMessage = codeMessage;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ErrorMessage that = (ErrorMessage) o;

        if (!codeMessage.equals(that.codeMessage)) return false;
        return textMessage.equals(that.textMessage);
    }

    @Override
    public int hashCode() {
        int result = codeMessage.hashCode();
        result = 31 * result + textMessage.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ErrorMessage{" +
                "codeMessage=" + codeMessage +
                ", textMessage='" + textMessage + '\'' +
                '}';
    }
}
