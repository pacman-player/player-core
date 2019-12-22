package spring.app.dao.abstraction;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import spring.app.model.Author;

import java.util.List;

@Repository
public interface CustomizedAuthorCrudRepository extends CrudRepository<Author, Long> {

    /**
     * находит всех авторов, у которых совпадает имя
     * @return List<Author>
     */
    List<Author> findAuthorsByNameContaining(String name);
}
