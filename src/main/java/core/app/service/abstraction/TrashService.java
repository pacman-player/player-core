package core.app.service.abstraction;

import java.io.IOException;

public interface TrashService<E> {
    void moveToTrash(E entity) throws IOException, InterruptedException;
}
