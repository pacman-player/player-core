package spring.app.dao.abstraction.dto;

import spring.app.dto.TagDto;

import java.util.List;

public interface TagDtoDao {

    List<TagDto> getAll(int pageSize, int pageNo);

    public Long getRowsCount();

}
