package spring.app.service.abstraction;

import spring.app.model.Author;

import java.util.List;

public interface AuthorService {
    Author getByName(String name);
    void addAuthor(Author author);
    List<Author> getAllAuthors();
}
