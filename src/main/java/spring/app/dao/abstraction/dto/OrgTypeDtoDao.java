package spring.app.dao.abstraction.dto;

import spring.app.dto.OrgTypeDto;

import java.util.List;

public interface OrgTypeDtoDao {

    List<OrgTypeDto> getAll();
}
