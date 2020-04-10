package core.app.dao.abstraction;

import core.app.model.Message;

public interface MessageDao extends GenericDao<Long, Message> {
    Message getByName(String name);
}
