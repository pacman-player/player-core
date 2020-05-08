package spring.app.service.abstraction;

import spring.app.dto.TagDto;
import spring.app.model.Tag;

import java.util.List;

public interface TagService {

    void addTag(Tag tag);

    List<TagDto> getAllTagDto();

    boolean isExistByName(String name);

    Tag getById(Long id);

    void updateTag(Tag tag);

    void deleteById(Long id);
}
