package spring.app.service.abstraction;

import spring.app.model.Author;

public interface AuthorService {
    Author getByName(String name);
    void addAuthor(Author author);
    Author getAuthorById(Long id);
}
