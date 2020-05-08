package spring.app.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.TagDao;
import spring.app.dao.abstraction.dto.TagDtoDao;
import spring.app.dto.TagDto;
import spring.app.model.Tag;
import spring.app.service.abstraction.TagService;

import java.util.List;

@Service
public class TagSeviceImpl implements TagService {

    private final TagDao tagDao;
    private final TagDtoDao tagDtoDao;

    public TagSeviceImpl(TagDao tagDao, TagDtoDao tagDtoDao) {
        this.tagDao = tagDao;
        this.tagDtoDao = tagDtoDao;
    }

    @Override
    @Transactional
    public void addTag(Tag tag) {
        tagDao.save(tag);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagDto> getAllTagDto() {
        return tagDtoDao.getAll();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExistByName(String name) {
        return tagDao.isExistByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public Tag getById(Long id) {
        return tagDao.getById(id);
    }

    @Override
    @Transactional
    public void updateTag(Tag tag) {
        tagDao.update(tag);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        tagDao.deleteById(id);
    }

}