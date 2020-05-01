package spring.app.service.abstraction;

import spring.app.model.PlayList;

public interface PlayListService extends GenericService<Long, PlayList> {

    PlayList getByName(String name);
}
