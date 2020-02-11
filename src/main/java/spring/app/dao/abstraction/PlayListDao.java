package spring.app.dao.abstraction;

import spring.app.model.PlayList;

public interface PlayListDao extends GenericDao<Long, PlayList> {

    PlayList getByName (String name);
}

