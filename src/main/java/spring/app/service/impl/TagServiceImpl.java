package spring.app.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.TagDao;
import spring.app.dao.abstraction.dto.TagDtoDao;
import spring.app.dto.TagDto;
import spring.app.model.Tag;
import spring.app.service.abstraction.TagService;

import java.util.List;
import java.util.Set;

@Service
public class TagServiceImpl extends AbstractServiceImpl<Long, Tag, TagDao> implements TagService {

    private final TagDtoDao tagDtoDao;

    public TagServiceImpl(TagDao dao, TagDtoDao tagDtoDao) {
        super(dao);
        this.tagDtoDao = tagDtoDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagDto> getAllTagDto(int pageSize, int pageNo) {
        return tagDtoDao.getAll(pageSize, pageNo);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getRowsCountTagDto() {
        return tagDtoDao.getRowsCount();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExistByName(String name) {
        return dao.isExistByName(name);
    }

    @Override
    public Set<Tag> findTags(String searchRequest) {
        return dao.findTags(searchRequest);
    }

}