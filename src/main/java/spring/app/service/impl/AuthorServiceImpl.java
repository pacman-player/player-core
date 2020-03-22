package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.AuthorDao;
import spring.app.model.Author;
import spring.app.model.Song;
import spring.app.service.abstraction.AuthorService;
import spring.app.service.abstraction.NotificationService;

import java.sql.Timestamp;
import java.util.List;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;
    private NotificationService notificationService;

    @Autowired
    public AuthorServiceImpl(AuthorDao authorDao, NotificationService notificationService) {
        this.authorDao = authorDao;
        this.notificationService = notificationService;
    }

    @Override
    public List<Author> getAllAuthor() {
        return authorDao.getAll();
    }

    @Override
    public void addAuthor(Author author) {
        authorDao.save(author);
        String name = author.getName();

        String message = "Был дабавлен новый автор " + name + " , нужно проверить жанры по "
                + " <a href=\"performers\">ссылке</a>" ;
        try {
            notificationService.addNotification(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Author getByName(String name) {
        return authorDao.getByName(name);
    }

    @Override
    public List<Author> findAuthorsByNameContaining(String name) {
        return authorDao.findByNameContaining(name);
    }

    @Override
    public Author getById(long authorsId) {
        return authorDao.getById(authorsId);
    }

    @Override
    public void updateAuthor(Author author) {
        authorDao.update(author);
    }

    @Override
    public void deleteAuthorById(Long id) {
        authorDao.deleteById(id);
    }

    @Override
    public List<Author> getByCreatedDateRange(Timestamp dateFrom, Timestamp dateTo) {
        return authorDao.getByCreatedDateRange(dateFrom, dateTo);
    }

    @Override
    public boolean isExist(String name) {
        return authorDao.isExist(name);
    }

}