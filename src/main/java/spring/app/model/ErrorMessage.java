package spring.app.model;

import org.springframework.http.HttpStatus;

public class ErrorMessage {
    private int codeMessage;
    private String textMessage;

    public int getCodeMessage() {
        return codeMessage;
    }

    public void setCodeMessage(int codeMessage) {
        this.codeMessage = codeMessage;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }



    @Override
    public String toString() {
        return "ErrorMessage{" +
                "codeMessage=" + codeMessage +
                ", textMessage='" + textMessage + '\'' +
                '}';
    }
}
