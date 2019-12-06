package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.app.dao.abstraction.AuthorDao;
import spring.app.model.Author;
import spring.app.service.abstraction.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    @Autowired
    public AuthorServiceImpl(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Override
    public void addAuthor(Author author) {
        authorDao.save(author);
    }
}

    @Override
    public Author getByName(String name) {
        return authorDao.getByName(name);
    }
}