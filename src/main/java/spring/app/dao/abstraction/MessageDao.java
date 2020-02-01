package spring.app.dao.abstraction;

import spring.app.model.Message;

public interface MessageDao extends GenericDao<Long, Message> {
    Message getByName(String name);
}
