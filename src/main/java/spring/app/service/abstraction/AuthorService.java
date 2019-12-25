package spring.app.service.abstraction;

import spring.app.model.Author;

import java.util.List;

public interface AuthorService {

    List<Author> getAllAuthor();

    Author getByName(String name);

    void addAuthor(Author author);

    Author getById(Long id);

    void updateAuthor(Author author);

    void deleteAuthorById(Long id);
}
