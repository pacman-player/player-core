package spring.app.dao.abstraction.dto;

import spring.app.dto.SongQueueDto;
import spring.app.model.Company;

import java.util.List;

public interface SongQueueDtoDao {
    List<SongQueueDto> getQueuesByCompanyId(Company company);
}
