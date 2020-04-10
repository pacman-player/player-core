package core.app.service.abstraction;

import java.io.IOException;

public interface TrashApiService<E> {

    boolean moveToTrash(E entity) throws IOException, InterruptedException;
}
