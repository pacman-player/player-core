package spring.app.dto;

import spring.app.model.Notification;

public class NotificationDto {
    private Long id;
    private String message;
    private Boolean flag;
    private String userName;
    private UserDto userDto;

    public NotificationDto() {
    }

    public NotificationDto(Long id, String message, Boolean flag, UserDto userDto) {
        this.id = id;
        this.message = message;
        this.flag = flag;
        this.userDto = userDto;
    }

    public NotificationDto(Long id, String message, Boolean flag, String userName) {
        this.id = id;
        this.message = message;
        this.flag = flag;
        this.userName = userName;
    }

    public NotificationDto(Long id, String message, Boolean flag) {
        this.id = id;
        this.message = message;
        this.flag = flag;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public NotificationDto(Notification notification) {
        this.id = notification.getId();
        this.message = notification.getMessage();
        this.flag = notification.getFlag();
        this.userName = notification.getUser().getLogin();
        if (notification.getUser() == null) {
            this.userName = "";      // то возвращаем пустую строк иначе ошибка на фронте
        } else {
            this.userName = notification.getUser().getLogin();
        }
    }
}
