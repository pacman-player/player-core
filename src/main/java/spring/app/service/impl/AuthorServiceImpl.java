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
    public void addAuthor(Author author) {
        authorDao.save(author);
        NotificationTemplate notificationTemplate = notificationTemplateService.getByName("default");

        try {
            notificationService.addNotification(author);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateAuthor(Author author) {
        authorDao.update(author);
    }

    /**
     * Когда удаляется Автор, удаляются все его песни
     */
    @Override
    public void deleteAuthorById(Long id) {
        // удаляем песни с данным автором
        songDao.bulkRemoveSongsByAuthorId(id);
        // теперь удаляем автора
        authorDao.deleteById(id);
    }

    @Override
    public Author getById(long authorsId) {
        return authorDao.getById(authorsId);
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
    public List<Author> getByCreatedDateRange(Timestamp dateFrom, Timestamp dateTo) {
        return authorDao.getByCreatedDateRange(dateFrom, dateTo);
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorDao.getAll();
    }

    @Override
    public List<Author> getAllApprovedAuthors() {
        return authorDao.getAllApproved();
    }

    @Override
    public List<Author> getApprovedAuthorsPage(int pageNumber, int pageSize) {
        return authorDao.getApprovedPage(pageNumber, pageSize);
    }

    @Override
    public int getLastApprovedAuthorsPageNumber(int pageSize) {
        return authorDao.getLastApprovedPageNumber(pageSize);
    }

    @Override
    public boolean isExist(String name) {
        return authorDao.isExist(name);
    }

}