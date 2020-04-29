package spring.app.dao.abstraction.dto;

import spring.app.dto.NotificationDto;

import java.util.List;

public interface NotificationDtoDao {

    List<NotificationDto> getAll();

    NotificationDto getNotificationById(long id);

}
