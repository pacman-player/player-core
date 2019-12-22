package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.app.dao.abstraction.AuthorDao;
import spring.app.dao.abstraction.CustomizedAuthorCrudRepository;
import spring.app.model.Author;
import spring.app.service.abstraction.AuthorService;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;
    private final CustomizedAuthorCrudRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorDao authorDao,
                             CustomizedAuthorCrudRepository authorRepository) {
        this.authorDao = authorDao;
        this.authorRepository = authorRepository;
    }

    @Override
    public void addAuthor(Author author) {
        authorDao.save(author);
    }

    @Override
    public Author getByName(String name) {
        return authorDao.getByName(name);
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorDao.getAll();
    }

    @Override
    public List<Author> findAuthorsByNameContaining(String name) {
        return authorRepository.findAuthorsByNameContaining(name);
    }
}