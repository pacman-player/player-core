package core.app.service.impl;

import core.app.model.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import core.app.dao.abstraction.AuthorDao;
import core.app.dao.abstraction.SongDao;
import core.app.model.NotificationTemplate;
import core.app.service.abstraction.AuthorService;
import core.app.service.abstraction.NotificationService;
import core.app.service.abstraction.NotificationTemplateService;

import java.sql.Timestamp;
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
    public void save(Author author) {
        authorDao.save(author);
        NotificationTemplate notificationTemplate = notificationTemplateService.getByName("default");

        try {
            notificationService.addNotification(author);
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
    public List<Author> getByCreatedDateRange(Timestamp dateFrom, Timestamp dateTo) {
        return authorDao.getByCreatedDateRange(dateFrom, dateTo);
    }

    @Override
    public boolean isExist(String name) {
        return authorDao.isExist(name);
    }

    @Override
    public void deleteEntity(Author entity) {
        // удаляем песни с данным автором
        songDao.bulkRemoveSongsByAuthorId(entity.getId());
        // теперь удаляем автора
        authorDao.deleteEntity(entity);
    }
}