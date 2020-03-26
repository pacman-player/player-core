package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.AuthorDao;
import spring.app.dao.abstraction.SongDao;
import spring.app.model.Author;
import spring.app.model.NotificationTemplate;
import spring.app.service.abstraction.AuthorService;
import spring.app.service.abstraction.NotificationService;
import spring.app.service.abstraction.NotificationTemplateService;

import java.util.List;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;
    private SongDao songDao;
    private NotificationService notificationService;
    private NotificationTemplateService notificationTemplateService;

    @Autowired
    public AuthorServiceImpl(AuthorDao authorDao, SongDao songDao, NotificationService notificationService, NotificationTemplateService notificationTemplateService) {
        this.authorDao = authorDao;
        this.songDao = songDao;
        this.notificationService = notificationService;
        this.notificationTemplateService = notificationTemplateService;
    }

    @Override
    public List<Author> getAllAuthor() {
        return authorDao.getAll();
    }

    @Override
    public void addAuthor(Author author) {
        authorDao.save(author);
        String name = author.getName();
        NotificationTemplate notificationTemplate = notificationTemplateService.getByName("default");

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

    /** Когда удаляется Автор, удаляются все его песни */
    @Override
    public void deleteAuthorById(Long id) {
        // удаляем песни с данным автором
        songDao.bulkRemoveSongsByAuthorId(id);
        // теперь удаляем автора
        authorDao.deleteById(id);
    }

    @Override
    public boolean isExist(String name) {
        return authorDao.isExist(name);
    }

}