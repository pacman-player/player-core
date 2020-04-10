package core.app.dao.abstraction;

import core.app.model.PlayList;

public interface PlayListDao extends GenericDao<Long, PlayList> {

    PlayList getByName (String name);
}

