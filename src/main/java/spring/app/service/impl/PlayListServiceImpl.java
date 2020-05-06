package spring.app.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.PlayListDao;
import spring.app.model.PlayList;
import spring.app.service.abstraction.PlayListService;

@Service
public class PlayListServiceImpl extends AbstractServiceImpl<Long, PlayList, PlayListDao> implements PlayListService {

    protected PlayListServiceImpl(PlayListDao dao) {
        super(dao);
    }

    @Override
    public PlayList getByName(String name) {
        return dao.getByName(name);
    }
}
