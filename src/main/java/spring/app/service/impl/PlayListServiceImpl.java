package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.OrgTypeDao;
import spring.app.dao.abstraction.PlayListDao;
import spring.app.model.OrgType;
import spring.app.model.PlayList;
import spring.app.service.abstraction.PlayListService;

@Service
@Transactional
public class PlayListServiceImpl extends AbstractService<PlayList, PlayListDao>  implements PlayListService {

    protected PlayListServiceImpl(PlayListDao dao) {
        super(dao);
    }

    @Override
    public void addPlayList(PlayList playList) {
        dao.save(playList);
    }

    @Override
    public PlayList getPlayList(Long id) {
        return dao.getById(id);
    }

    @Override
    public PlayList getPlayListByName(String name) {
        return dao.getByName(name);
    }
}
