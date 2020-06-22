package spring.app.service.abstraction;

import spring.app.dto.TagDto;
import spring.app.model.Tag;

import java.util.List;
import java.util.Set;

public interface TagService extends GenericService<Long, Tag> {

    List<TagDto> getAllTagDto();

    boolean isExistByName(String name);

    Set<Tag> findTags(String searchRequest);

}
