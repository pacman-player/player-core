package spring.app.dto;

public class ConversationDto {
    private String id;
    private String message;

    public Integer getId() {
        Integer id = Integer.parseInt(this.id);
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
